package com.mx.CineFuncion.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mx.CineFuncion.Dominio.Cine;
import com.mx.CineFuncion.Service.CineImplementacion;

@RestController // Indica que es un controlador REST
@CrossOrigin // Permite solicitudes desde cualquier origen
@RequestMapping("/cine") // Ruta base para las solicitudes relacionadas con cines
public class CineController {
	
	@Autowired //Inyección de dependencia
	private CineImplementacion cineService;
	
	@GetMapping("/listar")
	public ResponseEntity<?> listarCines() {
		//Guardamos la lista en otra lista para validar
		List<Cine> cines = cineService.listarC();
		
		//Validamos que el servicio devuelva la lista de cines
		if(cines.isEmpty()) { //Si la lista está vacía
			return ResponseEntity.noContent().build(); //Devuelve un estado 204 No Content
		} else {
			return ResponseEntity.ok(cines); //Devuelve un estado 200 OK con la lista de cines
		}
	}
	
	@PostMapping("/guardar")
	public ResponseEntity<String> guardarCine(@RequestBody Cine cine) {
		//Verificamos si el cine ya existe
		boolean existe = cineService.existeCine(cine.getNombre(), cine.getCiudad());
		
		if(existe) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("El cine ya existe");
		} else {
			cineService.guardarC(cine);
			return ResponseEntity.ok("Cine " + cine.getNombre() + " guardado correctamente");
		}
	}
	
	@PutMapping("/editar")
	public ResponseEntity<String> editarCine(@RequestBody Cine cine) {
		//Primero buscamos si el cine existe
		Cine cineExistente = cineService.buscarC(cine.getIdCine());
		
		if(cineExistente == null) { //Si no existe o no lo encuentra
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El cine no existe");
		} else {
			cineService.editarC(cine);
			return ResponseEntity.ok("Cine " + cine.getNombre() + " editado correctamente");
		}
	}
	
	@PostMapping("/buscarPorNombre")
	public ResponseEntity<?> buscarCinePorNombre(@RequestParam String nombre) {
		//Buscamos el cine por nombre
		Cine cine = cineService.buscarPorNombre(nombre);
		
		if(cine == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cine no encontrado");
		} else {
			return ResponseEntity.ok(cine);
		}
	}
	
	@DeleteMapping("/eliminar/{idCine}")
	public ResponseEntity<String> eliminarCine(@PathVariable Integer idCine) {
		//Primero buscamos si el cine existe
		Cine cineExistente = cineService.buscarC(idCine);
		
		if(cineExistente == null) { //Si no existe o no lo encuentra
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El cine no existe");
		} else {
			cineService.eliminarC(idCine);
			return ResponseEntity.ok("Cine eliminado correctamente");
		}
	}
	
	
}
