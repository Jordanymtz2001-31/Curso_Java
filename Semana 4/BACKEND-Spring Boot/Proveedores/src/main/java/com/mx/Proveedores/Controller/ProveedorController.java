package com.mx.Proveedores.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
	@PostMapping("/guardar")
	public ResponseEntity<?> guardarProveedor(@RequestBody Proveedor proveedor){
		try {
			dao.guardar(proveedor);
			return ResponseEntity.status(HttpStatus.CREATED).body("Proveedor guardado correctamente");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocurrio un error: " + e.getMessage());
		}
	}
	
	//Metodo de editar un proveedor
	@PostMapping("/editar")
	public ResponseEntity<?> editarProveedor(@RequestBody Proveedor proveedor){
		try {
			Proveedor existe = dao.buscar(proveedor.getIdProveedor());
			if (existe == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proveedor no encontrado");
			}else {
				dao.editar(proveedor);
				return ResponseEntity.status(HttpStatus.OK).body("Proveedor editado correctamente");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocurrio un error: " + e.getMessage());
		}

	}
	
	//Metodo de eliminar un proveedor
	@DeleteMapping("/eliminar/{idProveedor}")
	public ResponseEntity<?> eliminarProveedor(@PathVariable Proveedor idProveedor){
		try {
			Proveedor existe = dao.buscar(idProveedor.getIdProveedor());
			if (existe == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proveedor no encontrado");
				}else {
					dao.eliminar(idProveedor.getIdProveedor());
					return ResponseEntity.status(HttpStatus.OK).body("Proveedor eliminado correctamente");
				}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocurrio un error" + e.getMessage());
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
