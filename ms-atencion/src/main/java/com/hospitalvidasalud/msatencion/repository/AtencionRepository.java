package com.hospitalvidasalud.msatencion.repository;

import com.hospitalvidasalud.msatencion.entity.Atencion;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AtencionRepository extends ReactiveCrudRepository<Atencion, Long> {
}