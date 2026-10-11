package com.hospitalvidasalud.mscita.controller;

import com.hospitalvidasalud.mscita.dto.PacienteDTO;
import com.hospitalvidasalud.mscita.entity.Cita;
import com.hospitalvidasalud.mscita.repository.CitaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/citas")
public class CitaController {

    private final CitaRepository citaRepository;
    private final WebClient.Builder webClientBuilder;

    public CitaController(CitaRepository citaRepository,
                          WebClient.Builder webClientBuilder) {
        this.citaRepository = citaRepository;
        this.webClientBuilder = webClientBuilder;
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
}