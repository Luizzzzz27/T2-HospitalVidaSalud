package com.hospitalvidasalud.mspaciente.controller;

import com.hospitalvidasalud.mspaciente.entity.Paciente;
import com.hospitalvidasalud.mspaciente.repository.PacienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/pacientes")
public class PacienteController {

    private final PacienteRepository pacienteRepository;

    public PacienteController(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @GetMapping("/{id}")
    public Mono<Paciente> obtenerPorId(@PathVariable Long id) {
        return pacienteRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Paciente no encontrado")));
    }

    @PostMapping
    public Mono<ResponseEntity<Paciente>> registrar(@RequestBody Paciente paciente) {
        return pacienteRepository.existsByNumeroDocumento(paciente.getNumeroDocumento())
                .flatMap(existe -> {
                    if (existe) {
                        return Mono.error(new ResponseStatusException(
                                HttpStatus.BAD_REQUEST, "Paciente duplicado"));
                    }
                    paciente.setId(null);
                    paciente.setEstado("ACTIVO");
                    return pacienteRepository.save(paciente);
                })
                .map(guardado -> ResponseEntity.status(HttpStatus.CREATED).body(guardado));
    }
}