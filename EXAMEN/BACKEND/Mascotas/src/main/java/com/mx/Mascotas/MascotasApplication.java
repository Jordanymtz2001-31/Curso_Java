package com.mx.Mascotas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient  //Indica que este servicio se puede registrar en un servidor de descubrimiento como Eureka
public class MascotasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MascotasApplication.class, args);
	}

}
