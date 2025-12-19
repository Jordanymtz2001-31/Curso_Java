package com.mx.Cliente.Contoller;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import com.mx.Cliente.Service.ClienteImplementacion;
import com.mx.Cliente.Entidad.Cliente;

@RestController //Indica que es un controlador de Spring MVC
@RequestMapping("/clientes") //Mapea las solicitudes HTTP a /clientes
//@CrossOrigin(origins = "*") //Permite solicitudes CORS desde cualquier origen
public class ClienteController {
	
	@Autowired
	private ClienteImplementacion clienteServicio;
	
	@GetMapping("/lista")
	public ResponseEntity<?> listarClientes() {
		//Verifica si la lista esta vacia
		if (clienteServicio.listar().isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(clienteServicio.listar());
		}
	}
	
	//Metodo para guardar un cliente
	@PostMapping("/guardar")
	public ResponseEntity<Map<String, String>> guardarCliente(@RequestBody Cliente cliente) {
		try {
			boolean existeTelefono = clienteServicio.existeTelefono(cliente.getTelefono());
			
			if (existeTelefono) {
				return ResponseEntity.status(409).body(Map.of("error", "El telefono ya existe"));
			} else {
				boolean existeCliente = clienteServicio.existeCliente(cliente.getNombre(), cliente.getApellidoP(), cliente.getApellidoM());
				if (existeCliente) {
					return ResponseEntity.status(409).body(Map.of("error", "El cliente ya existe"));
				} else {
					clienteServicio.guardar(cliente);
					return ResponseEntity.status(201).body(Map.of("message", "Cliente guardado con éxito"));
				}
			}
			
		} catch (Exception e) {
			Map<String, String> error = Map.of("error al guardar", e.getMessage());
	        return ResponseEntity.badRequest().body(error);  // Status 400 para errores
		}
		
	}
	
	//Metodo para editar
	@PatchMapping("/editar")
	public ResponseEntity<Map<String, String>> editarCliente(@RequestBody Cliente cliente) {
		Map<String, String> response = new HashMap<>(); //Crea un mapa para la respuesta
		try {
			Cliente existente = clienteServicio.buscar(cliente.getId());
			if (existente == null) {
				response.put("Eror", "El cliente no existe"); //En el mapa agregamos un mensaje
				return ResponseEntity.status(404).body(response); //Retornamos el mapa con un estatus y un mensaje
			} else {
				clienteServicio.editar(cliente);
				response.put("mensaje", "Cliente editado con exito");
				return ResponseEntity.status(200).body(response);		
			}
			
		}catch (Exception e) {
			Map<String, String> error = Map.of("error al editar", e.getMessage());
	        return ResponseEntity.badRequest().body(error);  // Status 400 para errores
		}
		
	}
	
	//Metodo para buscar
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscar(@PathVariable Integer id){
		Cliente existe = clienteServicio.buscar(id);
		if(existe == null) {
			return ResponseEntity.status(400).body("Cliente no encontrado");
		}else {
			return ResponseEntity.ok(existe);
		}
	}
		
	//Metodo para eliminar
	@DeleteMapping("/eliminar/{idCliente}")
	public ResponseEntity<?> eliminar(@PathVariable Integer idCliente){
		Cliente existe = clienteServicio.buscar(idCliente);
		if(existe == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "El cliente no existe"));
		}else {
			clienteServicio.eliminar(idCliente);
			return ResponseEntity.ok(Map.of("message", "Cliente elimonado correctamente")); 
		}
	}
	//-------------------------------------------------------------------------------------------
	
	//METODOS PERSONALIZADOS
	
	
	//Listar por tienda
	@GetMapping("/listarXTienda/{tiendaId}")
	public ResponseEntity<?> buscarXTienda(@PathVariable Integer tiendaId){
		List<Cliente> clientes = clienteServicio.listarPorTienda(tiendaId);
		return ResponseEntity.ok(clientes);
	}
	

}
