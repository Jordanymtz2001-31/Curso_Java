package com.mx.Tienda.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.Tienda.Entity.Tienda;
import com.mx.Tienda.Service.TiendaService;

@RestController
@CrossOrigin
@RequestMapping("/tiendas")
public class TiendaContoller {
	
	@Autowired //Inyecta el servicio de tiendas
	private TiendaService service;
	
	//Metodo de listar todas las tiendas
	@GetMapping("/listar")
	public ResponseEntity<?> listarTiendas() {
		if (service.listarTiendas().isEmpty()) { //Si no hay tiendas registradas
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay tiendas registradas");
		} else {
			return ResponseEntity.status(HttpStatus.CREATED).body( service.listarTiendas());
		}
	}
	
	//Metodo para guardar una tienda
	@PostMapping("/guardar")
	public ResponseEntity<?> guardarTienda(@RequestBody Tienda tienda) {
		//Metodo para guardar una tienda
		try {
			service.guardar(tienda);
			return ResponseEntity.status(HttpStatus.CREATED).body("Tienda guardada correctamente");
		} catch (Exception e) {
			return ResponseEntity.ok("Ocurrio un error: " + e.getMessage());
		}
	}
	
	//Metodo para editar una tienda
	@PutMapping("/editar")
	public ResponseEntity<?> editarTienda(@RequestBody Tienda tienda) {
		Tienda exisT = service.buscarTienda(tienda.getIdTienda());
			if (exisT != null) {
			service.editarTienda(tienda);
			return ResponseEntity.status(HttpStatus.CREATED).body("Tienda editada correctamente");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La tienda no existe");
		}
	}
	
	//Metodo para eliminar una tienda
	@DeleteMapping("/eliminar/{idTienda}")
	public ResponseEntity<?> eliminarTienda(@PathVariable int idTienda) {
		Tienda exisT = service.buscarTienda(idTienda);
		if (exisT != null) {
			service.eliminarTienda(idTienda);
			return ResponseEntity.status(HttpStatus.CREATED).body("Tienda eliminada correctamente");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La tienda no existe");
		}
	}
	
	//Metodo para buscar una tienda por id
	@GetMapping("/buscar/{idTienda}")
	public ResponseEntity<?> buscarTienda(@PathVariable int idTienda) {
		Tienda tienda = service.buscarTienda(idTienda);
		if (tienda != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(tienda);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La tienda no existe");
		}
	}
	
	//---------------------------------------------------------------------------------------------
	
	//Metodo para buscar una tienda por nombre
	@GetMapping("/buscarNombre/{nombre}")
	public ResponseEntity<?> buscarTiendaPorNombre(@PathVariable String nombre) {
		Tienda tienda = service.buscarTiendaPorNombre(nombre);
		if (tienda != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(tienda);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La tienda no existe");
		}
	}
	
	//Metodo para listar por ciudad
	@PostMapping("/listarPorCiudad")
	public ResponseEntity<?> listarPorCiudad(@RequestParam String ciudad) {
		if (service.buscarPorCiudad(ciudad).isEmpty()) { //Si no hay tiendas en la ciudad
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay tiendas en la ciudad: " + ciudad);
		} else {
			return ResponseEntity.status(HttpStatus.CREATED).body(service.buscarPorCiudad(ciudad));
		}
	}
	
	//-------------------------------------------------------------------------------------------
	
	//METODOS DEL OTROS MICROSERVICIOS USANDO FEIGN CLIENT
	
	//Metodo para listar departamentos por tienda
	@GetMapping("/departamentos/{tiendaId}")
	public ResponseEntity<?> listarDepartamentosPorTienda(@PathVariable int tiendaId) {
		try {//Validar si la tienda existe
			if(service.buscarTienda(tiendaId) == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La tienda no existe");
			}else { //Si existe, esntonces Validamos si existe el departamento
				if(service.listarDepartamenXTienda(tiendaId).isEmpty()) { //Si no hay departamentos en la tienda
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay departamentos en la tienda con ID: " + tiendaId);
				}else {
					return ResponseEntity.ok(service.listarDepartamenXTienda(tiendaId));
				}
			}
				
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrio un error: " + e.getMessage());
			}
	}
	
	//Metodo para lista Empleados por tienda
	@GetMapping("/empleados/{tiendaId}")
	public ResponseEntity<?> listarEmpleadosPorTienda(@PathVariable int tiendaId) {
		try {//Validar si la tienda existe la tienda
			if(service.buscarTienda(tiendaId) == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La tienda no existe");
			}else { //Si existe, esntonces Validamos si existe el empleado
				if(service.listarEmpleadosXTienda(tiendaId).isEmpty()) { //Si no hay empleados en la tienda
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay empleados en la tienda con ID: " + tiendaId);
				}else {
					return ResponseEntity.ok(service.listarEmpleadosXTienda(tiendaId));
				}
			}
				
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrio un error: " + e.getMessage());
			}
	}
	
	//Metodo para listar clientes por tienda
	@GetMapping("/clientes/{tiendaId}")
	public ResponseEntity<?> listarClientesPorTienda(@PathVariable int tiendaId) {
		try {//Validar si la tienda existe la tienda
			if(service.buscarTienda(tiendaId) == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La tienda no existe");
			}else { //Si existe, esntonces Validamos si existe el cliente
				if(service.listarClientesXTienda(tiendaId).isEmpty()) { //Si no hay clientes en la tienda
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay clientes en la tienda con ID: " + tiendaId);
				}else {
					return ResponseEntity.ok(service.listarClientesXTienda(tiendaId));
				}
			}
				
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrio un error: " + e.getMessage());
			}
	}
	
	//Metodo para listar proveedores por tienda
	@GetMapping("/proveedores/{tiendaId}")
	public ResponseEntity<?> listarProveedoresPorTienda(@PathVariable int tiendaId) {
		try {//Validar si la tienda existe la tienda
			if(service.buscarTienda(tiendaId) == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La tienda no existe");
			}else { //Si existe, esntonces Validamos si existe el proveedor
				if(service.listarProveedoresXTienda(tiendaId).isEmpty()) { //Si no hay proveedores en la tienda
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay proveedores en la tienda con ID: " + tiendaId);
				}else {
					return ResponseEntity.ok(service.listarProveedoresXTienda(tiendaId));
				}
			}
				
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrio un error: " + e.getMessage());
			}
	}
	
	//Metodo para listar productos por tienda
	@GetMapping("/productos/{tiendaId}")
	public ResponseEntity<?> listarProductosPorTienda(@PathVariable int tiendaId) {
		try {//Validar si la tienda existe la tienda
			if(service.buscarTienda(tiendaId) == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La tienda no existe");
			}else { //Si existe, esntonces Validamos si existe el producto
				if(service.listarProductosXTienda(tiendaId).isEmpty()) { //Si no hay productos en la tienda
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay productos en la tienda con ID: " + tiendaId);
				}else {
					return ResponseEntity.ok(service.listarProductosXTienda(tiendaId));
				}
			}
				
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrio un error: " + e.getMessage());
			}
	}
	
	
	//Metodo para mostrar todos los datos de la tienda de los otros micro servicios
	@GetMapping("/info/{tiendaId}")
	public ResponseEntity<?> mostrarInfoTienda(@PathVariable int tiendaId) {
		return ResponseEntity.ok(service.mostrarInfo(tiendaId));
	}
	

}
