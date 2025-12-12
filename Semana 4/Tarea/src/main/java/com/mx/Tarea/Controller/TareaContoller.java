package com.mx.Tarea.Controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.Tarea.Entity.Eestado;
import com.mx.Tarea.Entity.Tareas;
import com.mx.Tarea.Service.TareaService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/tareas")
public class TareaContoller {
	
	@Autowired //Inyecta el servicio de tareas
	private TareaService service;
	
	//Metodo para listar todas las tareas
	@GetMapping("/listar")
	public ResponseEntity<?> listarTareas() {
		if (service.listarTareas().isEmpty()) { //Si no hay tareas registradas
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay tareas registradas");
		} else {
			return ResponseEntity.status(HttpStatus.CREATED).body( service.listarTareas());
		}
	}
	
	//Metodo para guardar una tarea
	@PostMapping("/guardar")
	public ResponseEntity<?> guardarTarea(@RequestBody Tareas tarea) {
		if (tarea.getTitulo() == null || tarea.getDescripcion() == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Todos los campos son obligatorios");
		} else { //Y verificamos que la fecha de vencimiento no sea anterior a la fecha de creacion
			try {
				service.guardarTarea(tarea);
				return ResponseEntity.status(HttpStatus.CREATED).body("Tarea guardada correctamente");
			} catch (IllegalArgumentException e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocurrio un error: " + e.getMessage());
			}
			
			
		}
	}
	
	//Metodo para editar una tarea
	@PutMapping("/editar")
	public ResponseEntity<?> editarTarea(@RequestBody Tareas tarea) {
		if (tarea.getTitulo() == null || tarea.getDescripcion() == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Todos los campos son obligatorios");
		} else {
			Tareas existe = service.buscar(tarea.getIdTarea());
			if (existe == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarea no encontrada");
			}else {//SI si entonces verificamos la fecha de vencimiento no sea anterior a la fecha de creacion
				try {
					service.editarTarea(tarea);
					return ResponseEntity.status(HttpStatus.OK).body("Tarea editada correctamente");
				} catch (IllegalArgumentException e) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocurrio un error: " + e.getMessage());
				}
			}
		}
	}
	
	//Metodo para eliminar una tarea
	@DeleteMapping("/eliminar/{idTarea}")
	public ResponseEntity<?> eliminarTarea(@PathVariable Tareas idTarea) {
		Tareas existe = service.buscar(idTarea.getIdTarea());
		//Si es nulo, lanza una excepcion
		if (existe == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarea no encontrada");
		}else {
			service.eliminarTarea(existe.getIdTarea());
			return ResponseEntity.status(HttpStatus.OK).body("Tarea eliminada correctamente");
		}
	}
	
	//Metodo para buscar una tarea por id
	@GetMapping("/buscar/{idTarea}")
	public ResponseEntity<?> buscarTarea(@PathVariable int idTarea) {
		Tareas existe = service.buscar(idTarea);
		if (existe == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarea no encontrada");
		}else {
			return ResponseEntity.status(HttpStatus.OK).body(existe);
			
		}
	}
	
	//Metodo para editar el estado de una tarea
	@PatchMapping("/editarEstado/{idTarea}/estado")
	public ResponseEntity<Tareas> editarEstadoTarea(@PathVariable int idTarea, @RequestBody Map<String, String> respuesta) {
		String nuevoEstado = respuesta.get("estado");
		
		try {//Cambiamos el estado de la tarea
			Tareas tareaActualizada = service.cambiarEstado(idTarea, nuevoEstado);
			return ResponseEntity.status(HttpStatus.OK).body(tareaActualizada);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	
	

}
