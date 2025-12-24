package com.mx.Responsables.Controller;

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

import com.mx.Responsables.Dto.MascotaDto;
import com.mx.Responsables.Entity.Responsable;
import com.mx.Responsables.Service.ResponsableService;

@RequestMapping("/responsable")
@RestController
public class ResponsableController {
	
	@Autowired //Inyectamos las dependencias 
	private ResponsableService service;
	
	//Metodo para listar
	@GetMapping("/lista")
	public ResponseEntity<?> Lista(){
		if (service.listar().isEmpty()) { //Si esta vacia entonces
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay Responsables registradas aun");
		}else {
			return ResponseEntity.ok(service.listar());
		}
	}
	
	@PostMapping("/guardar")
	public ResponseEntity<Map<String, String>> Guardar(@RequestBody Responsable responsable){
		
		try {
			boolean existeResponsable = service.existeNombre(responsable.getNombre());
			boolean existeNumero = service.existeTelefono(responsable.getContacto());
			
			if (existeResponsable) {
				return ResponseEntity.status(409).body(Map.of("error", "El Encargado ya existe"));
				
			}else if(existeNumero) {
		        return ResponseEntity.status(409).body(Map.of("error", "El número de contacto ya está registrado"));
		    }
			
			service.guardar(responsable);
			return ResponseEntity.status(201).body(Map.of("Mansaje", "Encargado guardado con exito"));

		}catch (Exception e) {
			Map<String, String> error = Map.of("error al guardar", e.getMessage());
	        return ResponseEntity.badRequest().body(error);  // Status 400 para errores
		}
	}
	
	
	@PutMapping("/editar")
	public ResponseEntity<Map<String, String>> Editar(@RequestBody Responsable responsable){
		
		Map<String, String> response = new HashMap<>(); //Crea un mapa para la respuesta
		try {
			
			//Buscamos primero si existe
			Responsable responsableExistente = service.buscarId(responsable.getIdResponsable());
			
			if(responsableExistente == null) {
				response.put("Eror", "El responsable no existe"); //En el mapa agregamos un mensaje
				return ResponseEntity.status(404).body(response); //Retornamos el mapa con un estatus y un mensaje
			}
			//Si es diferente a los datos que ya existen entonces si los edita
			service.editar(responsable);
			response.put("mensaje", "El responsable editado con exito");
			return ResponseEntity.status(200).body(response);
			
			
		}catch (Exception e) {
			Map<String, String> error = Map.of("error al editar", e.getMessage());
	        return ResponseEntity.badRequest().body(error);  // Status 400 para errores
		}
		
	}
	
	@GetMapping("/buscarNombre/{nombre}") //PERZONALIZADO
	public ResponseEntity<?> Buscar(@PathVariable String nombre){
		Responsable responsable = service.buscar(nombre);
		if(responsable == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El responsable no existe");
		}else {
			return ResponseEntity.ok(responsable);
		}
	}
	
	@GetMapping("/buscarId/{idResponsable}") //PERZONALIZADO
	public ResponseEntity<?> Buscar(@PathVariable Integer idResponsable){
		Responsable responsable = service.buscarId(idResponsable);
		if(responsable == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El responsable no existe");
		}else {
			return ResponseEntity.ok(responsable);
		}
	}
	
	@DeleteMapping("/eliminar/{idResponsable}")
	public ResponseEntity<?> Eliminar(@PathVariable Integer idResponsable){
		Responsable existe = service.buscarId(idResponsable);
		if(existe == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "El Responsable no existe"));
		}else {
			service.eliminar(idResponsable);
			return ResponseEntity.ok(Map.of("message", "El responsable eliminado correctamente")); 
		}
	}
	
	//METODOS PERSONALIZADOS-----------------------------------------------------------------------
	//EndPoint para listar responsables por veterinaria
	@GetMapping("/listaResponsables/{veterinariaId}")
	public ResponseEntity<?> ListarResponPorVeterinaria(@PathVariable int veterinariaId){
		List<Responsable> responsables = service.listarXVeterinaria(veterinariaId);
			
		if (responsables.isEmpty()) { //Si esta vacia la lista
			return ResponseEntity.ok("No hay Responsables registrados para esta veterinaria");	
		}
			
		return ResponseEntity.ok(responsables);
	}
	
	//Metodo para listar mascotas por  RESPONSABLE
	@GetMapping("/listarMascota/{responsableId}")
	public ResponseEntity<?> listarMascotas(@PathVariable int responsableId){
		try {
			Responsable resp = service.buscarId(responsableId);
			if(resp == null) {	
				return ResponseEntity.badRequest().body("El Responsable no existe");
			}else { 
				//Validar si las mascotas que vienen del otro microservicio estan vacios
				
				List<MascotaDto> emps = service.listarMascotas(responsableId);
				if(emps.isEmpty()) {
					return ResponseEntity.noContent().build();
				}else {
					return ResponseEntity.ok(emps);
				}
			}
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrio un error, el servidor no esta diponible");
		}
	
	}

}
