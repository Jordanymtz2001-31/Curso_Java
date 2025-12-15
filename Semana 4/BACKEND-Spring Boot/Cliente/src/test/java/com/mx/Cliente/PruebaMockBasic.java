package com.mx.Cliente;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest // Indica que es una clase de prueba de Spring Boot
@AutoConfigureMockMvc // Configura MockMvc para pruebas de los controladores web
public class PruebaMockBasic {
	
	@Autowired
	private MockMvc mockMvc; // Objeto MockMvc para simular solicitudes HTTP y verificar respuestas
	
	@Test
	void saludoPredeterminado() throws Exception {
		this.mockMvc.perform(get("/")) //Simula una solicitud GET a la URL raíz
			.andDo(print()) // Imprime los detalles de la solicitud y respuesta para depuración
			.andExpect(status().isOk()) // Verifica que el estado de la respuesta sea 200 OK
			.andExpect(content().string(containsString("Hola Mundo"))); // Verifica que el contenido de la respuesta sea "Hola Mundo"
	}

}