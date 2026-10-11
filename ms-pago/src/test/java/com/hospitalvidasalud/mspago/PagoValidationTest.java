package com.hospitalvidasalud.mspago;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.hospitalvidasalud.mspago.controller.PagoController;
import com.hospitalvidasalud.mspago.dto.PagoRequest;
import com.hospitalvidasalud.mspago.entity.Pago;
import com.hospitalvidasalud.mspago.repository.PagoRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class PagoValidationTest {

    private PagoRepository pagoRepository;
    private PagoController pagoController;

    @BeforeEach
    void configurar() {
        pagoRepository = mock(PagoRepository.class);
        pagoController = new PagoController(pagoRepository);
    }

    @Test
    void montoPositivoEsAceptado() {
        PagoRequest request = new PagoRequest(10L, new BigDecimal("125.50"));
        when(pagoRepository.save(any(Pago.class)))
                .thenAnswer(invocacion -> {
                    Pago guardado = invocacion.getArgument(0);
                    guardado.setId(1L);
                    return Mono.just(guardado);
                });

        StepVerifier.create(pagoController.registrar(request))
                .assertNext(respuesta -> {
                    assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
                    assertNotNull(respuesta.getBody());
                    assertEquals(new BigDecimal("125.50"), respuesta.getBody().getMonto());
                    assertEquals("COMPLETADO", respuesta.getBody().getEstado());
                })
                .verifyComplete();
    }

    @Test
    void montoNegativoEsRechazado() {
        verificarMontoInvalido(new BigDecimal("-1.00"));
    }

    @Test
    void montoCeroEsRechazado() {
        verificarMontoInvalido(BigDecimal.ZERO);
    }

    private void verificarMontoInvalido(BigDecimal monto) {
        PagoRequest request = new PagoRequest(10L, monto);

        StepVerifier.create(pagoController.registrar(request))
                .expectErrorMatches(error -> error instanceof ResponseStatusException
                        && ((ResponseStatusException) error).getStatusCode()
                                .equals(HttpStatus.BAD_REQUEST)
                        && "El monto debe ser mayor a 0".equals(
                                ((ResponseStatusException) error).getReason()))
                .verify();

        verifyNoInteractions(pagoRepository);
    }
}