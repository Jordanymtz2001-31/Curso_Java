package com.mx.Empleado;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient // Habilita el cliente de descubrimiento de servicios
public class EmpleadoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmpleadoApplication.class, args);
	}

}
