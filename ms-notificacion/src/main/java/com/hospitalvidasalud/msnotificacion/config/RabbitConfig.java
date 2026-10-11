package com.hospitalvidasalud.msnotificacion.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String COLA_CITAS_CONFIRMADAS = "q.citas.confirmadas";

    @Bean
    public Queue colaCitasConfirmadas() {
        return QueueBuilder.durable(COLA_CITAS_CONFIRMADAS).build();
    }
}