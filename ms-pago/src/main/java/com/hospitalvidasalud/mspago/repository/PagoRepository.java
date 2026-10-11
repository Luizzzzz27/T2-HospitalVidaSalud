package com.hospitalvidasalud.mspago.repository;

import com.hospitalvidasalud.mspago.entity.Pago;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PagoRepository extends ReactiveCrudRepository<Pago, Long> {
}