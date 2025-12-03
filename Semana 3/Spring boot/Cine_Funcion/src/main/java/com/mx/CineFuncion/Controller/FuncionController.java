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

import com.mx.CineFuncion.Dominio.Funcion;
import com.mx.CineFuncion.Service.FuncionImplementacion;

@RestController // Indica que es un controlador REST
@CrossOrigin // Permite solicitudes desde cualquier origen
@RequestMapping("/funcion") // Ruta base para las solicitudes relacionadas con funciones
public class FuncionController {
	
	@Autowired //Inyección de dependencia
	private FuncionImplementacion funcionService;
	
	@GetMapping("/listar")
	public ResponseEntity<?> listarFunciones() {
		//Guardamos la lista en otra lista para validar
		List<Funcion> funciones = funcionService.listarF();
		
		//Validamos que el servicio devuelva la lista de funciones
		if(funciones.isEmpty()) { //Si la lista está vacía
			return ResponseEntity.noContent().build(); //Devuelve un estado 204 No Content
		} else {
			return ResponseEntity.ok(funciones); //Devuelve un estado 200 OK con la lista de funciones
		}
	}
	
	@PostMapping("/guardar")
	public ResponseEntity<String> guardarFuncion(@RequestBody Funcion funcion) {
		//Verificamos si la función ya existe
		boolean existe = funcionService.existeFuncion(funcion.getPelicula(), funcion.getHorarios());
		
		if(existe) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("La función ya existe");
		} else {
			funcionService.guardarF(funcion);
			return ResponseEntity.ok("Función de " + funcion.getPelicula() + " guardada correctamente");
		}
	}
	
	@PutMapping("/editar")
	public ResponseEntity<String> editarFuncion(@RequestBody Funcion funcion) {
		//Primero buscamos si la función existe
		Funcion funcionExistente = funcionService.buscarF(funcion.getIdFuncion());
		
		if(funcionExistente == null) { //Si no existe o no lo encuentra
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La función no existe");
		} else {
			funcionService.editarF(funcion);
			return ResponseEntity.ok("Función de " + funcion.getPelicula() + " editada correctamente");
		}
	}
	
	@PostMapping("/buscarPorPelicula")
	public ResponseEntity<?> buscarFuncionPorPelicula(@RequestParam String nombrePelicula) {
		//Primero buscamos si la función existe
		Funcion funcion = funcionService.buscarPorPelicula(nombrePelicula);
		
		if(funcion == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La función no existe");
		} else {
			return ResponseEntity.ok(funcion);
		}
	}
	
	@DeleteMapping("/eliminar/{idFuncion}")
	public ResponseEntity<String> eliminarFuncion(@PathVariable Integer idFuncion) {
		//Primero buscamos si la función existe
		Funcion funcionExistente = funcionService.buscarF(idFuncion);
		
		if(funcionExistente == null) { //Si no existe o no lo encuentra
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La función no existe");
		} else {
			funcionService.eliminarF(idFuncion);
			return ResponseEntity.ok("Función eliminada correctamente");
		}
	}

}
