package com.hospitalvidasalud.msnotificacion.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificacionConsumer {

    @RabbitListener(queues = "q.citas.confirmadas")
    public void recibirMensaje(String mensaje) {
        System.out.println(
                "[RABBITMQ NOTIFICACION ASINCRONA RECIBIDA] Mensaje: " + mensaje);
    }
}