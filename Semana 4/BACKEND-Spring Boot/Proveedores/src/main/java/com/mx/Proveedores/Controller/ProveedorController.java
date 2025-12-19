package com.mx.Proveedores.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.Proveedores.Entity.Proveedor;
import com.mx.Proveedores.Service.ProveedorService;

//@CrossOrigin(origins = "*") // Permite solicitudes CORS desde cualquier origen
@RestController // Indica que esta clase es un controlador REST de Spring
@RequestMapping("/proveedores") // Mapea las solicitudes HTTP a /proveedores a este controlador
public class ProveedorController {

    
	@Autowired // Inyecta el servicio de proveedores
	private ProveedorService dao;

	//Metodo para listar los porveedores
	@GetMapping("/listar")
	public ResponseEntity<?> listar(){
		if (dao.listar().isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay proveedores registrados");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(dao.listar());
		}
	}

	//Metodo de guardar un proveedor
	@PostMapping(value = "/guardar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> guardarProveedor(@RequestBody Proveedor proveedor) {
		try {
			dao.guardar(proveedor);
			Map<String, Object> success = Map.of(
		            "message", "Proveedor guardado correctamente",
		            "proveedor", proveedor  // Devuelve la Tienda para Angular
		    );
			return ResponseEntity.ok(success); //Pasamos el mapa como cuerpo de la respuesta
		} catch (IllegalArgumentException e) {
			Map<String, String> error = Map.of("error", e.getMessage());
	        return ResponseEntity.badRequest().body(error);  // Status 400 para errores
		} 
		
	}
	
	//Metodo de editar un proveedor
	@PatchMapping("/editar")
	public ResponseEntity<Map<String, String>> editarProveedor(@RequestBody Proveedor proveedor){
		Map<String, String> response = new HashMap<>(); //Crea un mapa para la respuesta
		try {
			Proveedor existe = dao.buscar(proveedor.getIdProveedor());
			if (existe == null) {
				response.put("Eror", "El Proveedor no existe"); //En el mapa agregamos un mensaje
				return ResponseEntity.status(404).body(response); //Retornamos el mapa con un estatus y un mensaje
			}else {
				response.put("mensaje", "Provedor editado con exito");
				return ResponseEntity.status(200).body(response);
			}
		} catch (Exception e) {
			Map<String, String> error = Map.of("error", e.getMessage());
	        return ResponseEntity.badRequest().body(error);  // Status 400 para errores
		}

	}
	
	//Metodo de eliminar un proveedor
	@DeleteMapping("/eliminar/{idProveedor}")
	public ResponseEntity<?> eliminarProveedor(@PathVariable Proveedor idProveedor){
		try {
			Proveedor existe = dao.buscar(idProveedor.getIdProveedor());
			if (existe == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "El Proveedor no existe"));
				}else {
					dao.eliminar(idProveedor.getIdProveedor());
					return ResponseEntity.ok(Map.of("message", "Proveedor elimonado correctamente")); 
				}
		} catch (Exception e) {
			Map<String, String> error = Map.of("error", e.getMessage());
	        return ResponseEntity.badRequest().body(error);  // Status 400 para errores
		}
	}
	
	//metodo para buscar un proveedor por id
	@GetMapping("/buscar/{idProveedor}")
	public ResponseEntity<?> buscarProveedor(@PathVariable int idProveedor){
		Proveedor proveedor = dao.buscar(idProveedor);
		if (proveedor == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proveedor no encontrado");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(proveedor);
		}
	}
	
	//--------------------------------------------------------------------------------
	
	//METODO PERSONALIZADOS
	
	
	//Metodo para listar los proveedores por tiendaId
	@GetMapping("/listarPorTienda/{tiendaId}")
	public ResponseEntity<?> listarPorTienda(@PathVariable int tiendaId){
		return ResponseEntity.status(HttpStatus.OK).body(dao.listarPorTiendaId(tiendaId));
		
	}

}
