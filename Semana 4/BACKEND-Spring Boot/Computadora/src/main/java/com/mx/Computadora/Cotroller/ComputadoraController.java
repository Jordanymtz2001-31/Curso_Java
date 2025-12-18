package com.mx.Computadora.Cotroller;

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

import com.mx.Computadora.Dominio.Computadora;
import com.mx.Computadora.Service.ComputadoraImplementacion;

@RestController // Indica que esta clase es un controlador REST
@CrossOrigin // Permite solicitudes desde cualquier origen
@RequestMapping("/api/computadoras") // Ruta base para las solicitudes de este controlador
public class ComputadoraController {
	
	@Autowired // Inyección de dependencia del servicio de implementación
	private ComputadoraImplementacion compuService;
	
	@GetMapping("/lista")
	public ResponseEntity<?> listar() {
		// Guarda la lista de computadoras obtenida del servicio
		List<Computadora> computadoras = compuService.listar();
		
		// Validamos si la lista está vacía
		if(computadoras.isEmpty()) {
			return ResponseEntity.badRequest().body("No hay computadoras registradas");
		}else {
			return ResponseEntity.ok(computadoras);
		}
	}
	
	@PostMapping("/guardar")
	public ResponseEntity<?> guardar(@RequestBody Computadora computadora) {
	// Validamos si ya existe una computadora con la misma marca y modelo
	Boolean existe = compuService.existeCompu(computadora.getMarca(), computadora.getModelo());
		if(existe) {
			return ResponseEntity.badRequest().body("Ya existe una computadora con la misma marca y modelo.");
			}else {
				// Guardamos la computadora
				compuService.gurdar(computadora);
				return ResponseEntity.ok("Computadora guardada correctamente.");
			}
		}	
	
	@PutMapping("/editar")
	public ResponseEntity<?> editar(@RequestBody Computadora computadora) {
		// Validamos si la computadora existe
		Computadora existente = compuService.buscar(computadora.getIdCompu());
		if(existente != null) {
			// Editamos la computadora
			compuService.editar(computadora);
			return ResponseEntity.ok("Computadora editada correctamente.");
			}else {
				return ResponseEntity.badRequest().body("La computadora no existe.");
			}
	}
	
	@DeleteMapping("/eliminar/{idCompu}")
	public ResponseEntity<?> eliminar(@PathVariable Integer idCompu) {
		// Validamos EL ID de la computadora existe
		Computadora existente = compuService.buscar(idCompu);
		if(existente != null) {
			// Eliminamos la computadora
			compuService.eliminar(idCompu);
			return ResponseEntity.ok("Computadora eliminada correctamente.");
			}else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El ID no existe.");
			}
	}
	
	@GetMapping("/buscar/{idCompu}")
	public ResponseEntity<?> buscar(@PathVariable Integer idCompu) {
		// Buscamos la computadora por ID
		Computadora computadora = compuService.buscar(idCompu);
		if(computadora != null) {
			return ResponseEntity.ok(computadora);
			}else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La computadora no existe.");
			}
	}
	
	// Endpoint para aplicar descuento a todas las computadoras
	@PostMapping("/aplicardescuento")
	public ResponseEntity<?> aplicarDescuento(@RequestParam Integer descuento) {
		try {
			compuService.aplicarDescuento(descuento);
			return ResponseEntity.ok("Descuento de " + descuento + "% aplicado correctamente.");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Error al aplicar el mensaje "+ e.getMessage());
		}	//Regresamos el mensaje de error que viene del lado del servicio
	}
	
}	
