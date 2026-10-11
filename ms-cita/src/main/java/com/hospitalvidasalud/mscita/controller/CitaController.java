package com.hospitalvidasalud.mscita.controller;

import com.hospitalvidasalud.mscita.dto.PacienteDTO;
import com.hospitalvidasalud.mscita.entity.Cita;
import com.hospitalvidasalud.mscita.repository.CitaRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/citas")
public class CitaController {

    private static final String COLA_CITAS_CONFIRMADAS = "q.citas.confirmadas";

    private final CitaRepository citaRepository;
    private final WebClient.Builder webClientBuilder;
    private final RabbitTemplate rabbitTemplate;

    public CitaController(CitaRepository citaRepository,
                          WebClient.Builder webClientBuilder,
                          RabbitTemplate rabbitTemplate) {
        this.citaRepository = citaRepository;
        this.webClientBuilder = webClientBuilder;
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping
    public Mono<ResponseEntity<Cita>> registrar(@RequestBody Cita cita) {
        return webClientBuilder.build()
                .get()
                .uri("http://ms-paciente/api/v1/pacientes/{pacienteId}", cita.getPacienteId())
                .retrieve()
                .onStatus(status -> status.value() == 404,
                        response -> Mono.error(new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Paciente no encontrado")))
                .bodyToMono(PacienteDTO.class)
                .flatMap(paciente -> {
                    cita.setId(null);
                    cita.setEstado("PENDIENTE");
                    return citaRepository.save(cita);
                })
                .map(guardada -> ResponseEntity.status(HttpStatus.CREATED).body(guardada));
    }

    @PutMapping("/{id}/confirmar")
    public Mono<ResponseEntity<Cita>> confirmar(@PathVariable Long id) {
        return citaRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cita no encontrada")))
                .flatMap(cita -> {
                    cita.setEstado("CONFIRMADA");
                    return citaRepository.save(cita);
                })
                .doOnNext(cita -> rabbitTemplate.convertAndSend(
                        COLA_CITAS_CONFIRMADAS,
                        "Cita confirmada para la cita ID: " + id))
                .map(ResponseEntity::ok);
    }
}