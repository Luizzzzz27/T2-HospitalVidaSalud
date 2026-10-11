package com.hospitalvidasalud.msatencion.controller;

import com.hospitalvidasalud.msatencion.dto.AtencionRequest;
import com.hospitalvidasalud.msatencion.entity.Atencion;
import com.hospitalvidasalud.msatencion.repository.AtencionRepository;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/atenciones")
public class AtencionController {

    private final AtencionRepository atencionRepository;

    public AtencionController(AtencionRepository atencionRepository) {
        this.atencionRepository = atencionRepository;
    }

    @PostMapping
    public Mono<ResponseEntity<Atencion>> registrar(@RequestBody AtencionRequest request) {
        Atencion atencion = new Atencion(
                null, request.getCitaId(), LocalDateTime.now(), "ATENDIDA");
        return atencionRepository.save(atencion)
                .map(guardada -> ResponseEntity.status(HttpStatus.CREATED).body(guardada));
    }
}