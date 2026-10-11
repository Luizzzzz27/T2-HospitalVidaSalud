package com.hospitalvidasalud.mspaciente.repository;

import com.hospitalvidasalud.mspaciente.entity.Paciente;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface PacienteRepository extends ReactiveCrudRepository<Paciente, Long> {

    Mono<Boolean> existsByNumeroDocumento(String numeroDocumento);
}