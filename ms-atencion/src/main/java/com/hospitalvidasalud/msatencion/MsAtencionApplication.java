package com.hospitalvidasalud.msatencion;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDiscoveryClient
@SpringBootApplication
public class MsAtencionApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsAtencionApplication.class, args);
	}

}
