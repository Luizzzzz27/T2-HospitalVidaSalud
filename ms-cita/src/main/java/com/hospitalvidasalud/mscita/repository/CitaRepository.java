package com.hospitalvidasalud.mscita.repository;

import com.hospitalvidasalud.mscita.entity.Cita;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CitaRepository extends ReactiveCrudRepository<Cita, Long> {
}