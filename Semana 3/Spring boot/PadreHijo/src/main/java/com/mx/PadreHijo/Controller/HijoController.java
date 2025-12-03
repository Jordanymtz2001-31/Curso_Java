package com.mx.PadreHijo.Controller;

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

import com.mx.PadreHijo.Dominio.Hijo;
import com.mx.PadreHijo.Service.HijoImplementacion;

@RestController //Indica que es un controlador REST
@CrossOrigin //Permite solicitudes desde cualquier origen
@RequestMapping("/hijo") //Ruta base para las solicitudes relacionadas con hijos
public class HijoController {
	
	@Autowired //Inyección de dependencia
	private HijoImplementacion hijoService;
	
	@GetMapping("/listar")
	public ResponseEntity<?> listarHijos() {
		//Guardamos la lista en otra lista para validar
		List<Hijo> hijos = hijoService.listarH();
		
		//Validamos que el servicio devuelva la lista de hijos
		if(hijos.isEmpty()) { //Si la lista está vacía
			return ResponseEntity.noContent().build(); //Devuelve un estado 204 No Content
		} else {
			return ResponseEntity.ok(hijos); //Devuelve un estado 200 OK con la lista de hijos
		}
	}
	
	@PostMapping("/guardar")
	public ResponseEntity<String> guardarHijo(@RequestBody Hijo hijo) {
		
		//Primero los buscamos para evitar duplicados por id
		Hijo hijogurdado = hijoService.buscarH(hijo.getIdHijo());
		
		//Validamos si no existe el hijo se guarda
		if(hijogurdado != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body("Hijo guardado correctamente");
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Error al guardar el hijo");
		}
	}
	
	@PutMapping("/editar")
	public ResponseEntity<String> editarHijo(@RequestBody Hijo hijo) {
		//Primero buscamos si el hijo existe
		Hijo hijoExistente = hijoService.buscarH(hijo.getIdHijo());
		
		if(hijoExistente == null) { //Si no existe o no lo encuentra
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El hijo no existe");
		} else {
			hijoService.editarH(hijo);
			return ResponseEntity.ok("Hijo " + hijo.getNombre() + " editado correctamente");
		}
	}
	
	@DeleteMapping("/eliminar/{idHijo}")
	public ResponseEntity<String> eliminarHijo(@PathVariable Integer idHijo) {
		//Primero buscamos si el hijo existe
		Hijo hijoExistente = hijoService.buscarH(idHijo);
		
		if(hijoExistente == null) { //Si no existe o no lo encuentra
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El hijo no existe");
		} else {
			hijoService.eliminarH(idHijo);
			return ResponseEntity.ok("Hijo eliminado correctamente");
		}
	}
	
	@GetMapping("/buscar/{idHijo}")	
	public ResponseEntity<?> buscarHijo(@PathVariable Integer idHijo) {
		Hijo hijo = hijoService.buscarH(idHijo);
		
		if(hijo == null) { //Si no existe o no lo encuentra
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El hijo no existe");
		} else {
			return ResponseEntity.ok(hijo);
		}
	}
	
	@PostMapping("/buscarPorHobbie")
	public ResponseEntity<?> buscarHijoPorHobbie(@RequestParam String hobbie) {
		//Creamos una lista para guardar los hijos encontrados por hobbie
		List<Hijo> hijosEncontrados = hijoService.buscarPorHobbie(hobbie);
		
		if(hijosEncontrados.isEmpty()) { //Si no encuentra hijos con ese hobbie
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron hijos con el hobbie: " + hobbie);
		} else {
			return ResponseEntity.ok(hijosEncontrados);
		}
	}

}
