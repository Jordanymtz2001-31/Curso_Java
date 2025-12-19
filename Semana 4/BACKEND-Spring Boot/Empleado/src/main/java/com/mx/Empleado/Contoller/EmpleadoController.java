package com.mx.Empleado.Contoller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.mx.Empleado.Entidad.Empelado;
import com.mx.Empleado.Services.EmpeladoServices;

@RestController // Indica que esta clase es un controlador REST de Spring
//@CrossOrigin(origins = "*") // Permite solicitudes CORS desde cualquier origen
@RequestMapping("/empleados") // Mapea las solicitudes HTTP a /empleados
public class EmpleadoController {
	
	@Autowired // Inyecta el servicio de empleados
	private EmpeladoServices empleadoServices;
	
	// Endpoint para listar todos los empleados
	@GetMapping("/listar")
	public ResponseEntity<?> ListarEmpleados() {
		if(empleadoServices.listar().isEmpty()) {// Verifica si la lista de empleados está vacía
			return ResponseEntity.noContent().build();
		}else {
			return ResponseEntity.ok(empleadoServices.listar()); // Devuelve la lista de empleados con estado 200 OK
		}
	}
	 
	//EndPoint para guardar un empleado
	@PostMapping("/guardar")
	public ResponseEntity<Map<String, String>> GuardarEmpleado(@RequestBody Empelado empleado){
		try {
			boolean existeEmpleado = empleadoServices.existeEmpleado(empleado.getNombre(), empleado.getApellidoP(), empleado.getApellidoM());
			boolean existeTelefono = empleadoServices.existeTelefono(empleado.getTelefono());
			if(existeEmpleado) {
				return ResponseEntity.status(409).body(Map.of("error", "El Empleado ya existe"));
				}else { 
					if(existeTelefono) {
						return ResponseEntity.status(409).body(Map.of("error", "El telefono ya existe"));
					}else if(empleado.getTelefono() == null){
						return ResponseEntity.status(400).body(Map.of("error", "El telefono es obligatorio"));
					}else {
						empleadoServices.guadar(empleado);
						return ResponseEntity.status(201).body(Map.of("Mansaje", "Empleado guardado con exito"));
					}
				}
			
		}catch (Exception e) {
			Map<String, String> error = Map.of("error al guardar", e.getMessage());
	        return ResponseEntity.badRequest().body(error);  // Status 400 para errores
		}
		
	}
	
	//EndPoint para editar un empleado
	@PatchMapping("/editar")
	public ResponseEntity<Map<String, String>> EditarEmpleado(@RequestBody Empelado empleado){
		Map<String, String> response = new HashMap<>(); //Crea un mapa para la respuesta
		try {
			Empelado empleadoExistente = empleadoServices.buscar(empleado.getId());
			if(empleadoExistente == null) {
				response.put("Eror", "El Empleado no existe"); //En el mapa agregamos un mensaje
				return ResponseEntity.status(404).body(response); //Retornamos el mapa con un estatus y un mensaje
			}else {
				response.put("mensaje", "Empleado editado con exito");
				return ResponseEntity.status(200).body(response);
			}
		}catch (Exception e) {
			Map<String, String> error = Map.of("error al editar", e.getMessage());
	        return ResponseEntity.badRequest().body(error);  // Status 400 para errores
		}
		
	}
	
	//EndPoint para buscar
	@GetMapping("buscar/{id}")
	public ResponseEntity<?> BuscarEmpleado(@PathVariable int id){
		Empelado empleado = empleadoServices.buscar(id);
		if(empleado == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El empleado no existe");
		}else {
			return ResponseEntity.ok(empleado);
		}
	}
	
	//EndPoint para eliminar un empleado
	@DeleteMapping("eliminar/{id}")
	public ResponseEntity<?> EliminarEmpleado(@PathVariable int id){
		Empelado empleado = empleadoServices.buscar(id);
		if(empleado == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "El cliente no existe"));
		}else {
			empleadoServices.eliminar(id);
			return ResponseEntity.ok(Map.of("message", "Cliente elimonado correctamente")); 
		}
	}
	
	//METODO PERSONALIZADOS
	
	@GetMapping("/listDepa/{departamentoId}")
	public ResponseEntity<?> ListarPorDepartamentoId(@PathVariable int departamentoId){
		if(empleadoServices.listarPorDepartamentoId(departamentoId).isEmpty()) { // Verifica si la lista de empleados está vacía
			return ResponseEntity.noContent().build();
		}else {
			return ResponseEntity.ok(empleadoServices.listarPorDepartamentoId(departamentoId));
		}
	}
	
	@GetMapping("/listTienda/{tiendaId}")
	public ResponseEntity<?> ListarPorTiendaId(@PathVariable int tiendaId){
		
			return ResponseEntity.ok(empleadoServices.listarPorTiendaId(tiendaId));
		
	}

}
