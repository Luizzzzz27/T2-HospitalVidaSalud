package com.hospitalvidasalud.mspaciente;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDiscoveryClient
@SpringBootApplication
public class MsPacienteApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsPacienteApplication.class, args);
	}

}
