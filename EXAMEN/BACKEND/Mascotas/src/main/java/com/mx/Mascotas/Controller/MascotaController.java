package com.mx.Mascotas.Controller;

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

import com.mx.Mascotas.Entity.Mascota;
import com.mx.Mascotas.Services.MascotaService;

@RestController
@RequestMapping("/mascota")
public class MascotaController {
	
	@Autowired //Inyectamos las dependencias de Service
	private MascotaService service;
	
	@GetMapping("/lista")
	public ResponseEntity<?> Lista(){
		if (service.listar().isEmpty()) { //Si esta vacia entonces
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay mascotas registradas aun");
		}else {
			return ResponseEntity.ok(service.listar());
		}
	}
	
	@PostMapping("/guardar")
	public ResponseEntity<Map<String, String>> Guardar(@RequestBody Mascota mascota){
		
		try {
			if(mascota.getNombre()==null || mascota.getRazonCita() == null) {
				return ResponseEntity.badRequest().body(Map.of("error", "El nombre y la Razon de la cita deben de conter los datos"));
			}
				service.guardar(mascota);
				return ResponseEntity.status(201).body(Map.of("Mensaje", "Mascota guardado Correctamento"));
			
		}catch (Exception e) {
			Map<String, String> error = Map.of("error al guardar", e.getMessage());
	        return ResponseEntity.badRequest().body(error);  // Status 400 para errores
		}
	}
	
	@PutMapping("/editar")
	public ResponseEntity<Map<String, String>> Editar(@RequestBody Mascota mascota){
		Map<String, String> response = new HashMap<>();
		Mascota mascotaexiste = service.buscarId(mascota.getIdMascota());
		
		try {
			if(mascotaexiste == null) {
				response.put("Error", "La mascota no existe");
				return ResponseEntity.status(409).body(response);
			}
			
			service.editar(mascota);
			response.put("Mensaje", "La mascota editada Correctamente");
			return ResponseEntity.ok(response);
			
		}catch (Exception e) {
			response.put("error", e.getMessage());
			return ResponseEntity.status(500).body(response);  
			
		}
	}
	
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> Eliminar(@PathVariable int id){
		Mascota existe = service.buscarId(id);
		if(existe == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "La mascota no existe"));
		}else {
			service.eliminar(id);
			return ResponseEntity.ok(Map.of("message", "La mascota eliminada correctamente")); 
		}
	}
	
	@GetMapping("/buscarId/{idMascota}")
	public ResponseEntity<?> BuscarMascota(@PathVariable int idMascota){
		Mascota mascota = service.buscarId(idMascota);
		if(mascota == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La Mascota no existe");
		}else {
			return ResponseEntity.ok(mascota);
		}
	}
	
	
	//METODOS PERSONALIZADOS-------------------------------------------------------------------------
	
	@GetMapping("/buscarNombre/{nombre}")
	public ResponseEntity<?> BuscarMascota(@PathVariable String nombre){
		Mascota mascota = service.buscar(nombre);
		if(mascota == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La Mascota no existe");
		}else {
			return ResponseEntity.ok(mascota);
		}
	}
	
	//EndPoint para listar mascotas por cliente
	@GetMapping("/cliente/{clienteId}")
	public ResponseEntity<List<Mascota>> ListarMascotaPorCliente(@PathVariable int clienteId){
		List<Mascota> mascotas = service.listarIDCliente(clienteId);
		
		if (mascotas.isEmpty()) { //Si esta vacia la lista
			return ResponseEntity.noContent().build();		
		}
		
		return ResponseEntity.ok(mascotas);
	}
		
	//EndPoint para listar mascotas por veterinaria
	@GetMapping("/veterinaria/{veterinariaId}")
	public ResponseEntity<?> ListarMascotasPorVeterianria(@PathVariable int veterinariaId){
		List<Mascota> mascotas = service.listarIDVeterinaria(veterinariaId);
		
		if(mascotas.isEmpty()) {
			return ResponseEntity.ok("No hay mascotas registrados para esta Veterinaria");
		}
		
		return ResponseEntity.ok(mascotas); //200
		
		
	}
	
	//EndPoint para listar mascotas por Responsable
	@GetMapping("/responsable/{responsableId}")
	public ResponseEntity<?> ListarMascotaResponsable(@PathVariable int responsableId){
		List<Mascota> mascotas = service.listarIDResponsable(responsableId);
		
		if(mascotas.isEmpty()) { //si esta vacia
			return ResponseEntity.ok("No hay Mascotas registrados para este responsable");
		}
		
		return ResponseEntity.ok(mascotas); //200
		
	}

}
