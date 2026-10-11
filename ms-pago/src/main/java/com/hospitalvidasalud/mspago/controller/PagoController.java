package com.hospitalvidasalud.mspago.controller;

import com.hospitalvidasalud.mspago.dto.PagoRequest;
import com.hospitalvidasalud.mspago.entity.Pago;
import com.hospitalvidasalud.mspago.repository.PagoRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/pagos")
public class PagoController {

    private final PagoRepository pagoRepository;

    public PagoController(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    @PostMapping
    public Mono<ResponseEntity<Pago>> registrar(@RequestBody PagoRequest request) {
        if (request.getMonto() == null || request.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
            return Mono.error(new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "El monto debe ser mayor a 0"));
        }
        Pago pago = new Pago(null, request.getAtencionId(), request.getMonto(),
                LocalDateTime.now(), "COMPLETADO");
        return pagoRepository.save(pago)
                .map(guardado -> ResponseEntity.status(HttpStatus.CREATED).body(guardado));
    }
}