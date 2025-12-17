package com.mx.Empleado.Contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public ResponseEntity<?> GuardarEmpleado(@RequestBody Empelado empleado){
		boolean existeEmpleado = empleadoServices.existeEmpleado(empleado.getNombre(), empleado.getApellidoP(), empleado.getApellidoM());
		boolean existeTelefono = empleadoServices.existeTelefono(empleado.getTelefono());
		if(existeEmpleado) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("El empleado ya existe");
			}else {
				if(existeTelefono) {
					return ResponseEntity.status(HttpStatus.CONFLICT).body("El numero " + empleado.getTelefono() + "de telefono ya existe");
				}else if(empleado.getTelefono() == null){
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El numero de telefono no puede ser nulo");
				}else {
					empleadoServices.guadar(empleado);
					return ResponseEntity.status(HttpStatus.CREATED).body("Empleado guardado con exito");
				}
			}
	}
	
	//EndPoint para editar un empleado
	@PutMapping("/editar")
	public ResponseEntity<?> EditarEmpleado(@RequestBody Empelado empleado){
		Empelado empleadoExistente = empleadoServices.buscar(empleado.getId());
		if(empleadoExistente == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El empleado no existe");
	}else {
			empleadoServices.editar(empleado);
			return ResponseEntity.ok("Empleado editado con exito");
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
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El empleado no existe");
		}else {
			empleadoServices.eliminar(id);
			return ResponseEntity.ok("Empleado eliminado con exito");
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
