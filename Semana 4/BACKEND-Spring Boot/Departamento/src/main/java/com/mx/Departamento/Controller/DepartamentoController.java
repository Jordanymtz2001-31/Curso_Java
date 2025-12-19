package com.mx.Departamento.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mx.Departamento.Dto.EmpleadoDto;
import com.mx.Departamento.Dto.ProductoDto;
import com.mx.Departamento.Entidad.Departamento;
import com.mx.Departamento.Service.DepartamentoService;

@RestController // Indica que esta clase es un controlador de Spring
//@CrossOrigin // Permite solicitudes CORS desde cualquier origen
@RequestMapping("/departamento") // Mapea las solicitudes HTTP a /departamento
public class DepartamentoController {
	
	@Autowired //Inyecta el servicio de Departamento
	private DepartamentoService servicio;
	
	//Metodo para listar
	@GetMapping("/listar")
	public ResponseEntity<?> listar(){
		if(servicio.listar().isEmpty()) {
			return ResponseEntity.ok("No hay departamentos registrados");
			}else {
				
				return ResponseEntity.ok(servicio.listar());
			}
	}
	
	//Metodo para guardar
	@PostMapping("/guardar")
	public ResponseEntity<Map<String, String>> guardar(@RequestBody Departamento departamento){
		servicio.guardar(departamento);
		return ResponseEntity.status(201).body(Map.of("message", "Departamento guardado con éxito"));
	}
	
	//Metodo para editar
	@PatchMapping("/editar")
	public ResponseEntity<Map<String, String>> editar(@RequestBody Departamento departamento){
		Map<String, String> response = new HashMap<>(); //Crea un mapa para la respuesta
		try {
			Departamento dep = servicio.buscar(departamento.getIdDepartamento());
			if(dep != null) {
				servicio.editar(departamento);
				response.put("Mensaje", "Departamento editado Correctamente");
				return ResponseEntity.status(200).body(response);
				}else {
					response.put("Error", "El Departamento no existe");
					return ResponseEntity.status(404).body(response);
				}
		}catch (Exception e){
			Map<String, String> error = Map.of("error al editar", e.getMessage());
	        return ResponseEntity.badRequest().body(error);  // Status 400 para errores
		}
		
	}
	
	//Metodo para eliminar
	@DeleteMapping("/eliminar/{idDepartamento}")
	public ResponseEntity<?> eliminar(@PathVariable int idDepartamento){
		Departamento dep = servicio.buscar(idDepartamento);
		if(dep != null) {
			servicio.eliminar(idDepartamento);
			return ResponseEntity.ok(Map.of("message", "Departamento eliminado correctamente")); 
			}else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "El Departamento no existe"));
			}
	}
	
	//Metodo para buscar
	@GetMapping("/buscar/{idDepartamento}")
	public ResponseEntity<?> buscar(@PathVariable int idDepartamento){
		Departamento dep = servicio.buscar(idDepartamento);
		if(dep != null) {
			return ResponseEntity.ok(dep);
			}else {
				return ResponseEntity.ok("El departamento no existe");
			}
	}
	
	//Metodo para lista de departamentos por tienda
	@GetMapping("/listarPorTienda/{tiendaId}")
	public ResponseEntity<?> listarPorTienda(@PathVariable int tiendaId){
		if(servicio.listarPorTienda(tiendaId).isEmpty()) {
			return ResponseEntity.ok("No hay departamentos registrados para esta tienda");
			}else {
				
				return ResponseEntity.ok(servicio.listarPorTienda(tiendaId));
			}
	}
	
	//Metodo para listar empleados dependiendo del departamento
	@GetMapping("/listarEmpleados/{departamentoId}")
	public ResponseEntity<?> listarEmpleados(@PathVariable int departamentoId){
		try {
			Departamento depa = servicio.buscar(departamentoId);
			if(depa == null) {	
				return ResponseEntity.badRequest().body("El departamento no existe");
			}else { 
				//Validar si los empleados que vienen del otro microservicio estan vacios
				
				List<EmpleadoDto> emps = servicio.listarEmpleado(departamentoId);
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
	
	//Metodo para listar productos dependiendo del departamento
	@GetMapping("/listarProductos/{departamentoId}")
	public ResponseEntity<?> listarProductos(@PathVariable int departamentoId){
		try { //Captura cualquier excepción que pueda ocurrir al llamar al otro microservicio
			Departamento depa = servicio.buscar(departamentoId);
			if(depa == null) {	
				return ResponseEntity.badRequest().body("El departamento no existe");
			}else { 
				//Validar si los productos que vienen del otro microservicio estan vacios
				
				List<ProductoDto> prods = servicio.listarProducto(departamentoId);
				if(prods.isEmpty()) { // Si la lista de productos está vacía
					return ResponseEntity.noContent().build();
				}else {
					return ResponseEntity.ok(prods);
				}
			}
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrio un error, el servidor no esta diponible");
		}
	
	}

}
