package com.mx.Cliente;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

//Indica que es una clase de prueba de Spring Boot y en un puerto aleatorio para evitar conflictos
@AutoConfigureTestRestTemplate
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PruebaJunitBasic {
	
	@LocalServerPort // Inyecta el puerto aleatorio asignado al servidor de pruebas
	private int port;
	
	@Autowired
	TestRestTemplate restTemplate; // Cliente REST para realizar solicitudes HTTP en pruebas sumuladas y obtener respuestas
	
	@Test
	void saludoPredeterminado() throws Exception {
		// Realiza una solicitud GET a la URL raíz del servidor de pruebas y verifica que la respuesta sea "Hola Mundo"
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/", String.class)).contains("Hola Mundo");
	}
}
