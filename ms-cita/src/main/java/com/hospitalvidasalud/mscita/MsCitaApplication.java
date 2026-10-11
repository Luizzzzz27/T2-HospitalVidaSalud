package com.hospitalvidasalud.mscita;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDiscoveryClient
@SpringBootApplication
public class MsCitaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCitaApplication.class, args);
	}

}
