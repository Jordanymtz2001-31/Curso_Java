package com.mx.Cliente.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.Cliente.Dto.MascotaDto;
import com.mx.Cliente.Entity.Cliente;
import com.mx.Cliente.Service.ClienteService;

@RestController //Indica que esta clase es un controlador REST de Spring
@RequestMapping("/cliente") // Mapea las solicitudes HTTP a /empleados
public class ClienteController {
	
	@Autowired
	private ClienteService service;
	

	//Metodo para listar
	@GetMapping("/lista")
	public ResponseEntity<?> Lista(){
		if (service.listar().isEmpty()) { //Si esta vacia entonces
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay Clientes registradas aun");
		}else {
			return ResponseEntity.ok(service.listar());
		}
	}
	
	@PostMapping("/guardar")
	public ResponseEntity<Map<String, String>> Guardar(@RequestBody Cliente cliente){
		
		try {
			boolean existeCliente = service.existeNombre(cliente.getNombre());
			boolean existeNumero = service.existeTelefono(cliente.getContacto());
			boolean existeDireccion = service.existeDireccion(cliente.getDireccion());
			
			if (existeCliente) {
				return ResponseEntity.status(409).body(Map.of("error", "El Cliente ya existe"));
				
			}else if(existeNumero) {
		        return ResponseEntity.status(409).body(Map.of("error", "El número de contacto ya está registrado"));
		    
			}else if(existeDireccion) {
		        return ResponseEntity.status(409).body(Map.of("error", "La Direccion ya está registrado"));
			} 
			
			service.guardar(cliente);
			return ResponseEntity.status(201).body(Map.of("Mansaje", "Cliente guardado con exito"));

		}catch (Exception e) {
			Map<String, String> error = Map.of("error al guardar", e.getMessage());
	        return ResponseEntity.badRequest().body(error);  // Status 400 para errores
		}
	}
	
	
	@PutMapping("/editar")
	public ResponseEntity<Map<String, String>> Editar(@RequestBody Cliente cliente){
		
		Map<String, String> response = new HashMap<>(); //Crea un mapa para la respuesta
		try {
			//Buscamos primero si existe
			Cliente clienteExistente = service.buscarId(cliente.getIdCliente());
			
			if(clienteExistente == null) {
				response.put("Eror", "El cliente no existe"); //En el mapa agregamos un mensaje
				return ResponseEntity.status(404).body(response); //Retornamos el mapa con un estatus y un mensaje
				
			}
			//Si es diferente a los datos que ya existen entonces si los edita
			service.editar(cliente);
			response.put("mensaje", "El Cliente editado con exito");
			return ResponseEntity.status(200).body(response);
			
		}catch (Exception e) {
			Map<String, String> error = Map.of("error al editar", e.getMessage());
	        return ResponseEntity.badRequest().body(error);  // Status 400 para errores
		}
		
	}
	
	@GetMapping("/buscar/{nombre}") //PERZONALIZADO------------------------------
	public ResponseEntity<?> Buscar(@PathVariable String nombre){
		Cliente cliente = service.buscar(nombre);
		if(cliente == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El cliente no existe");
		}else {
			return ResponseEntity.ok(cliente);
		}
	}
	
	@DeleteMapping("/eliminar/{idCliente}")
	public ResponseEntity<?> Eliminar(@PathVariable Integer idCliente){
		Cliente existe = service.buscarId(idCliente);
		if(existe == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "El Cliente no existe"));
		}else {
			service.eliminar(idCliente);
			return ResponseEntity.ok(Map.of("message", "El Cliente eliminado correctamente")); 
		}
	}
	
	//METODO PERSONALIZADO QUE CONSUME A OTRO MICROSERVICIO
	//Metodo para listar mascotas por cliente
	@GetMapping("/listarMascota/{clienteId}")
	public ResponseEntity<?> listarMascotas(@PathVariable int clienteId){
		try {
			Cliente cliente = service.buscarId(clienteId);
			if(cliente == null) {	
				return ResponseEntity.badRequest().body("El Cliente no existe");
			}else { 
				//Validar si las mascotas que vienen del otro microservicio estan vacios
				
				List<MascotaDto> mascotas = service.listarMascotas(clienteId);
				if(mascotas.isEmpty()) {
					return ResponseEntity.noContent().build();
				}else {
					return ResponseEntity.ok(mascotas);
				}
			}
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrio un error, el servidor no esta diponible");
		}
	
	}
	
	

}
