package com.mx.Producto.Controller;

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
	public ResponseEntity<?> GuardarProducto(@RequestBody Producto producto){
		boolean existeProducto = dao.existeProducto(producto.getNombre());
		if(existeProducto) {
			return ResponseEntity.status(409).body("El producto ya existe");
			}else {
					dao.guardar(producto);
					return ResponseEntity.status(201).body("Producto guardado con exito");
				}
	}
	
	//EndPoint para editar un producto
	@PutMapping("/editar")
	public ResponseEntity<?> EditarProducto(@RequestBody Producto producto){
		Producto productoExistente = dao.buscar(producto.getId());
		if(productoExistente == null) {
			return ResponseEntity.status(404).body("El producto no existe");
		}else {
			dao.editar(producto);
			return ResponseEntity.ok("Producto editado con exito");
		}
	}
	
	//EndPoint para eliminar un producto
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> EliminarProducto(@PathVariable int id){
		Producto productoExistente = dao.buscar(id);
		if(productoExistente == null) {
			return ResponseEntity.status(404).body("El producto no existe");
		}else {
			dao.eliminar(id);
			return ResponseEntity.ok("Producto eliminado con exito");
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
