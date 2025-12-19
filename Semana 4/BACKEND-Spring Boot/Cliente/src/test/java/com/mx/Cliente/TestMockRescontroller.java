package com.mx.Cliente;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mx.Cliente.Contoller.ClienteController;
import com.mx.Cliente.Entidad.Cliente;
import com.mx.Cliente.Service.ClienteImplementacion;


@WebMvcTest(ClienteController.class) // Indica que es una prueba enfocada en el controlador ClienteController
public class TestMockRescontroller {
	
	@Autowired
	private MockMvc mockMvc; // Objeto MockMvc para simular solicitudes HTTP y verificar respuestas
	
	//Creamos un mock del servicio ClienteImplementacion 
	@MockitoBean
	private ClienteImplementacion service; // 
	
	//Prueba para el metodo listar clientes
	@Test
	void listarClienteTest() throws Exception {
		// Crear instancias de Cliente para las pruebas
		Cliente cliente = new Cliente(1,"juan","martinez", "jimenez", 25, 2331086955L, 1);
		Cliente cliente2 = new Cliente(2,"pedro","lopez", "gomez", 30, 2331086956L, 1); 
		
		List<Cliente> clientes = List.of(cliente, cliente2); // Crear una lista de clientes
		
		//Simulacion el servicio devuelve la lista  de clientes 
		//El thenReturn especifica el valor que debe devolver el metodo listar() del servicio mockeado
		when(service.listar()).thenReturn(clientes);
		
		//Convertir la lista de clientes a formato JSON esperado
		ObjectMapper mapper = new ObjectMapper();
		String jsonEsperado = mapper.writeValueAsString(clientes);
		
		// Realizar una solicitud GET al endpoint /clientes y verificar con los resultados esperados
		this.mockMvc.perform(get("/clientes/lista"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().json(jsonEsperado)); 
		
		
	}
	
	//Prueba Unitaria para el metodo guardar cliente
	@Test
	void guardarClienteTest() throws Exception {
		// Crear una instancia de Cliente para la prueba
		Cliente cliente = new Cliente(1,"juan","martinez", "jimenez", 25, 2331086955L, 1);
		
		//Simulacion del servicio para verificar que no existe el telefono ni el cliente y el false indica que no existen
		when(service.existeTelefono(cliente.getTelefono())).thenReturn(false);
		when(service.existeCliente(cliente.getNombre(), cliente.getApellidoP(), cliente.getApellidoM())).thenReturn(false);
		
		//Configuramos el mock del método guardar (usamos doNothing() para metodos void)
		doNothing().when(service).guardar(any(Cliente.class));
		
		//Convertir el cliente a formato JSON para enviar en la solicitud
		ObjectMapper mapper = new ObjectMapper();
		String jsonCliente = mapper.writeValueAsString(cliente);
		
		// Realizar una solicitud POST de guardar, espera que le pasemos el cuerpo en JSON
		this.mockMvc.perform(post("/clientes/guardar")
			.contentType(MediaType.APPLICATION_JSON_VALUE) // Indica que el contenido es JSON
			.content(jsonCliente)) 			// Envia el JSON del cliente en el cuerpo de la solicitud
			.andDo(print()) 				//Imprime detalles de la solicitud y respuesta
			.andExpect(status().isCreated()) // Esperamos un estado 201 Created del servidor
			.andExpect(content().string("Cliente guardado con exito"));  // Tambien esperamos este mensaje en la respuesta del servidor
		
	}
	
	//Prueba Unitaria para el metodo de editar cliente
	@Test
	void editarClienteTest() throws Exception {
		// Crear una instancia de Cliente para la prueba
		Cliente cliente = new Cliente(1,"juan","martinez", "jimenez", 25, 2331086955L, 1);
		
		//Simulacion del servicio para verificar que el cliente existe y colocamos con thenReturn el cliente existente
		when(service.buscar(cliente.getId())).thenReturn(cliente);
		
		//Configuramos el mock del método editar (usamos doNothing() para metodos void)
		doNothing().when(service).editar(any(Cliente.class));
		
		//Convertir el cliente a formato JSON para enviar en la solicitud
		ObjectMapper mapper = new ObjectMapper();
		String jsonCliente = mapper.writeValueAsString(cliente);
		
		// Realizar una solicitud POST de editar, espera que le pasemos el cuerpo en JSON
		this.mockMvc.perform(put("/clientes/editar")
			.contentType(MediaType.APPLICATION_JSON_VALUE) // Indica que el contenido es JSON
			.content(jsonCliente)) 			// Envia el JSON del cliente en el cuerpo de la solicitud
			.andDo(print()) 				//Imprime detalles de la solicitud y respuesta
			.andExpect(status().isOk()) // Esperamos un estado 200 OK del servidor
			.andExpect(content().string("Cliente editado con exito"));  // Tambien esperamos este mensaje en la respuesta del servidor
		
	}
	
	//Prueba Unitaria para el metodo de buscar cliente
	@Test
	void buscarClienteTest() throws Exception {
		// Crear una instancia de Cliente para la prueba
		Cliente cliente = new Cliente(1,"juan","martinez", "jimenez", 25, 2331086955L, 1);
		
		//Simulacion del servicio para verificar que el cliente existe y colocamos con thenReturn el cliente existente
		when(service.buscar(cliente.getId())).thenReturn(cliente);
		
		//Convertir el cliente a formato JSON esperado
		ObjectMapper mapper = new ObjectMapper();
		String jsonEsperado = mapper.writeValueAsString(cliente);
		
		// Realizar una solicitud GET al endpoint /clientes/buscar/{idCliente} y verificar con los resultados esperados
		this.mockMvc.perform(get("/clientes/buscar/{idCliente}", cliente.getId()))
			.andDo(print())
			.andExpect(status().isOk()) //Aqui esperamos un estado 200 OK del servidor
			.andExpect(content().json(jsonEsperado));  //Aqui si esperamos al cliente que se esta buscando en formato JSON
		
	}
	
	//Prueba Unitaria para el metodo de eliminar cliente
	@Test
	void eliminarClienteTest() throws Exception {
		// Crear una instancia de Cliente para la prueba
		Cliente cliente = new Cliente(1,"juan","martinez", "jimenez", 25, 2331086955L, 1);
		
		//Simulacion del servicio para verificar que el cliente existe y colocamos con thenReturn el cliente existente
		when(service.buscar(cliente.getId())).thenReturn(cliente);
		
		//Configuramos el mock del método eliminar (usamos doNothing() para metodos void)
		doNothing().when(service).eliminar(cliente.getId());
		
		// Realizar una solicitud GET al endpoint /clientes/eliminar/{idCliente} y verificar con los resultados esperados
		this.mockMvc.perform(delete("/clientes/eliminar/{idCliente}", cliente.getId()))
			.andDo(print())
			.andExpect(status().isOk()) //Aqui esperamos un estado 200 OK del servidor
			.andExpect(content().string("Eliminado"));  //Aqui esperamos este mensaje en la respuesta del servidor
		
	}
	
	//Prueba Unitaria para el metodo de listar clientes por tienda
	@Test
	void listarClientesPorTiendaTest() throws Exception {
		// Crear instancias de Cliente para las pruebas
		Cliente cliente = new Cliente(1,"juan","martinez", "jimenez", 25, 2331086955L, 1);
		Cliente cliente2 = new Cliente(2,"pedro","lopez", "gomez", 30, 2331086956L, 1); 
		
		List<Cliente> clientes = List.of(cliente, cliente2); // Crear una lista de clientes
		
		//Simulacion el servicio devuelve la lista  de clientes por tienda
		//El thenReturn especifica el valor que debe devolver el metodo listarPorTienda() del servicio mockeado
		when(service.listarPorTienda(1)).thenReturn(clientes);
		
		//Convertir la lista de clientes a formato JSON esperado
		ObjectMapper mapper = new ObjectMapper();
		String jsonEsperado = mapper.writeValueAsString(clientes);
		
		// Realizar una solicitud GET al endpoint /clientes/tienda/{tiendaId} y verificar con los resultados esperados
		this.mockMvc.perform(get("/clientes/listarXTienda/{tiendaId}", 1))
			.andDo(print())
			.andExpect(status().isOk()) //Esperamos un estado 200 OK del servidor
			.andExpect(content().json(jsonEsperado)); //Esperamos la lista de clientes en formato JSON
		
		
	}
	
	
	
	
	
	
	
	

}
