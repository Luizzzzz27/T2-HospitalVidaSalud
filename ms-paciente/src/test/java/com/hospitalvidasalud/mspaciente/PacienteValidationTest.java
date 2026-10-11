package com.hospitalvidasalud.mspaciente;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.hospitalvidasalud.mspaciente.controller.PacienteController;
import com.hospitalvidasalud.mspaciente.entity.Paciente;
import com.hospitalvidasalud.mspaciente.repository.PacienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class PacienteValidationTest {

    private PacienteRepository pacienteRepository;
    private PacienteController pacienteController;

    @BeforeEach
    void configurar() {
        pacienteRepository = mock(PacienteRepository.class);
        pacienteController = new PacienteController(pacienteRepository);
    }

    @Test
    void pacienteConDatosCompletosEsRegistrado() {
        Paciente paciente = new Paciente(
                null, "DNI", "74859612", "Ana", "Torres", null);
        when(pacienteRepository.existsByNumeroDocumento("74859612"))
                .thenReturn(Mono.just(false));
        when(pacienteRepository.save(any(Paciente.class)))
                .thenAnswer(invocacion -> {
                    Paciente guardado = invocacion.getArgument(0);
                    guardado.setId(1L);
                    return Mono.just(guardado);
                });

        StepVerifier.create(pacienteController.registrar(paciente))
                .assertNext(respuesta -> {
                    assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
                    assertNotNull(respuesta.getBody());
                    assertEquals("ACTIVO", respuesta.getBody().getEstado());
                    assertEquals("74859612", respuesta.getBody().getNumeroDocumento());
                })
                .verifyComplete();

        verify(pacienteRepository).save(paciente);
    }

    @Test
    void documentoDuplicadoEsRechazado() {
        Paciente paciente = new Paciente(
                null, "DNI", "74859612", "Ana", "Torres", null);
        when(pacienteRepository.existsByNumeroDocumento("74859612"))
                .thenReturn(Mono.just(true));

        StepVerifier.create(pacienteController.registrar(paciente))
                .expectErrorMatches(error -> error instanceof ResponseStatusException
                        && ((ResponseStatusException) error).getStatusCode()
                                .equals(HttpStatus.BAD_REQUEST)
                        && "Paciente duplicado".equals(
                                ((ResponseStatusException) error).getReason()))
                .verify();
    }

    @Test
    void documentoNuloEsRechazado() {
        Paciente paciente = new Paciente(
                null, "DNI", null, "Ana", "Torres", null);

        StepVerifier.create(pacienteController.registrar(paciente))
                .expectErrorMatches(error -> error instanceof ResponseStatusException
                        && ((ResponseStatusException) error).getStatusCode()
                                .equals(HttpStatus.BAD_REQUEST))
                .verify();

        verifyNoInteractions(pacienteRepository);
    }
}