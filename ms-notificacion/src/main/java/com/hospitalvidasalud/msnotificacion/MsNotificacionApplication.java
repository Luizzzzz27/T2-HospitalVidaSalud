package com.hospitalvidasalud.msnotificacion;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDiscoveryClient
@SpringBootApplication
public class MsNotificacionApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsNotificacionApplication.class, args);
	}

}
