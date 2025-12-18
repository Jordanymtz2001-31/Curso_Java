package com.mx.Producto.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.Producto.Entidad.Producto;
import com.mx.Producto.Service.ProductoService;

//@CrossOrigin // Permite solicitudes desde cualquier origen
@RestController // Indica que esta clase es un controlador REST de Spring
@RequestMapping("/productos") // Mapea las solicitudes HTTP a /productos
public class ProductoController {
	
	@Autowired // Inyecta el servicio de productos
	private ProductoService dao;
	
	// Endpoint para listar todos los productos
	@GetMapping("/listar")
	public ResponseEntity<?> ListarProductos() {
		if(dao.listar().isEmpty()) {// Verifica si la lista de productos está vacía
			return ResponseEntity.noContent().build();
		}else {
			return ResponseEntity.ok(dao.listar()); // Devuelve la lista de productos con estado 200 OK
		}
	}
	
	//EndPoint para guardar un producto
	@PostMapping("/guardar")
	public ResponseEntity<Map<String, String>> GuardarProducto(@RequestBody Producto producto){
		try {
	        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
	            return ResponseEntity.badRequest().body(Map.of("error", "El nombre del producto es requerido"));
	        }
	        
	        boolean existe = dao.existeProducto(producto.getNombre());
	        if (existe) {
	            return ResponseEntity.status(409)
	                .body(Map.of("error", "El producto ya existe"));
	        }
	        
	        dao.guardar(producto);
	        return ResponseEntity.status(201).body(Map.of("message", "Producto guardado con éxito"));
	    } catch (Exception e) {
	    	Map<String, String> error = Map.of("error al guardar", e.getMessage());
	        return ResponseEntity.badRequest().body(error);  // Status 400 para errores
	    }
	}
	
	//EndPoint para editar un producto
	@PatchMapping("/editar")
	public ResponseEntity<Map<String, String>> EditarProducto(@RequestBody Producto producto){
		Map<String, String> response = new HashMap<>();
	    
	    try {
	        if (dao.existeProducto(producto.getNombre())) {
	            response.put("error", "Producto ya existe");
	            return ResponseEntity.status(409).body(response);
	        }
	        
	        dao.editar(producto);
	        response.put("message", "Producto editado con éxito");
	        return ResponseEntity.ok(response);  // ← JSON: {"message": "..."}
	        
	    } catch (Exception e) {
	        response.put("error", e.getMessage());
	        return ResponseEntity.status(500).body(response);
	    }
	}
	
	//EndPoint para eliminar un producto
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> EliminarProducto(@PathVariable int id){
		Producto productoExistente = dao.buscar(id);
		if(productoExistente == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "El Producto no existe"));
		}else {
			dao.eliminar(id);
			return ResponseEntity.ok(Map.of("message", "Producto eliminada correctamente")); 
		}
	}
	
	//EndPoint para buscar un producto
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> BuscarProducto(@PathVariable int id){
		Producto producto = dao.buscar(id);
		if(producto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El empleado no existe");
		}else {
			return ResponseEntity.ok(producto);
		}
	}
	
	//METODO PERSONALIZADOS
	
	//EndPoint para listar productos por departamentoId
	@GetMapping("/departamento/{depaId}")
	public ResponseEntity<?> ListarProductosPorDepa(@PathVariable int depaId){
		if(dao.listarPorDepartamentoId(depaId).isEmpty()) {
			return ResponseEntity.status(404).body("No hay productos en este departamento");
			}else {
				return ResponseEntity.ok(dao.listarPorDepartamentoId(depaId));
			}
	}
	
	//EndPoint para listar productos por tiendaId
	@GetMapping("/tienda/{tiendaId}")
	public ResponseEntity<?> ListarProductosPorTienda(@PathVariable int tiendaId){
		Producto producto = dao.buscar(tiendaId);
		
		if(producto == null) {
			return ResponseEntity.status(404).body("No hay productos en esta tienda");
			}else {
				return ResponseEntity.ok(dao.listarPorTiendaId(tiendaId));
			}
	}
	
	
}
