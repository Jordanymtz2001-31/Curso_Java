package com.mx.Veterinaria.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.Veterinaria.Entity.Veterinaria;
import com.mx.Veterinaria.Services.VeterinariaService;


@RestController
@RequestMapping("/veterinaria")
public class VeterinariaController {
	
	@Autowired
	private VeterinariaService service;
	

	//Metodo para listar
	@GetMapping("/lista")
	public ResponseEntity<?> Lista(){
		if (service.listar().isEmpty()) { //Si esta vacia entonces
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay Veterinarias registradas aun");
		}else {
			return ResponseEntity.ok(service.listar());
		}
	}
	
	@PostMapping("/guardar")
	public ResponseEntity<Map<String, String>> Guardar(@RequestBody Veterinaria veterinaria){
		
		try {
			boolean existeVeterinaria = service.existeNombre(veterinaria.getNombre());
			boolean existeNumero = service.existeTelefono(veterinaria.getTelefono());
			boolean existeDireccion = service.existeDireccion(veterinaria.getDireccion());
			
			if (existeVeterinaria) {
				return ResponseEntity.status(409).body(Map.of("error", "La Veterinaria ya existe"));
				
			}else if(existeNumero) {
		        return ResponseEntity.status(409).body(Map.of("error", "El Número de contacto ya está registrado"));
		    
			}else if(existeDireccion) {
		        return ResponseEntity.status(409).body(Map.of("error", "La Direccion ya está registrado"));
			} 
			
			service.guardar(veterinaria);
			return ResponseEntity.status(201).body(Map.of("Mansaje", "Veterinaria guardado con exito"));

		}catch (Exception e) {
			Map<String, String> error = Map.of("error al guardar", e.getMessage());
	        return ResponseEntity.badRequest().body(error);  // Status 400 para errores
		}
	}
	
	
	@PutMapping("/editar")
	public ResponseEntity<Map<String, String>> Editar(@RequestBody Veterinaria veterinaria){
		
		Map<String, String> response = new HashMap<>(); //Crea un mapa para la respuesta
		try {
			//Buscamos primero si existe
			Veterinaria clienteVete = service.buscarId(veterinaria.getIdVeterinaria());
			
			if(clienteVete == null) {
				response.put("Eror", "La veterinaria no existe"); //En el mapa agregamos un mensaje
				return ResponseEntity.status(404).body(response); //Retornamos el mapa con un estatus y un mensaje
				
			}
			//Si es diferente a los datos que ya existen entonces si los edita
			service.editar(veterinaria);
			response.put("mensaje", "La Vveterinaria editado con exito");
			return ResponseEntity.status(200).body(response);
			
		}catch (Exception e) {
			Map<String, String> error = Map.of("error al editar", e.getMessage());
	        return ResponseEntity.badRequest().body(error);  // Status 400 para errores
		}
		
	}
	
	@GetMapping("/buscar/{nombre}") //PERZONALIZADO
	public ResponseEntity<?> Buscar(@PathVariable String nombre){
		Veterinaria veterinaria = service.buscar(nombre);
		if(veterinaria == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La Veterinaria no existe");
		}else {
			return ResponseEntity.ok(veterinaria);
		}
	}
	
	@DeleteMapping("/eliminar/{idVeterinaria}")
	public ResponseEntity<?> Eliminar(@PathVariable Integer idVeterinaria){
		Veterinaria existe = service.buscarId(idVeterinaria);
		if(existe == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "La veterinaria no existe"));
		}else {
			service.eliminar(idVeterinaria);
			return ResponseEntity.ok(Map.of("message", "Veterinaria eliminado correctamente")); 
		}
	}
	
	//METODOS DEL OTROS MICROSERVICIOS USANDO FEIGN CLIENT---------------------------------------------------------
	
	//Metodo para listar mascotas por veterinaria
	@GetMapping("/mascotas/{veterinariaId}")
	public ResponseEntity<?> listarMascotasXVeterinaria(@PathVariable int veterinariaId) {
		try {//Validar si la tienda existe
			if(service.buscarId(veterinariaId) == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La Veterinaria no existe");
			}else { //Si existe, esntonces Validamos si existe la mascota
				if(service.listarMXVeterinaria(veterinariaId).isEmpty()) { //Si no hay mascotas en esa Veterinaria
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay mascotas en la Veterinaria con ID: " + veterinariaId);
				}else {
					return ResponseEntity.ok(service.listarMXVeterinaria(veterinariaId));
				}
			}
				
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrio un error: " + e.getMessage());
			}
	}
	
	//Metodo para listar responsable por veterinaria
	@GetMapping("/responsables/{veterinariaId}")
	public ResponseEntity<?> listarResponsablesXVeterinaria(@PathVariable int veterinariaId) {
		try {//Validar si la tienda existe
			if(service.buscarId(veterinariaId) == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La Veterinaria no existe");
			}else { //Si existe, esntonces Validamos si existe la mascota
				if(service.listarRXVeterinaria(veterinariaId).isEmpty()) { //Si no hay mascotas en esa Veterinaria
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay Responsables en la Veterinaria con ID: " + veterinariaId);
				}else {
					return ResponseEntity.ok(service.listarRXVeterinaria(veterinariaId));
				}
			}
				
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrio un error: " + e.getMessage());
			}
	}
	

}
