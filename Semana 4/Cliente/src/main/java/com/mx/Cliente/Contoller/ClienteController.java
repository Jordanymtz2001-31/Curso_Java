package com.mx.Cliente.Contoller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import com.mx.Cliente.Service.ClienteImplementacion;
import com.mx.Cliente.Entidad.Cliente;

@RestController //Indica que es un controlador de Spring MVC
@RequestMapping("/clientes") //Mapea las solicitudes HTTP a /clientes
@CrossOrigin(origins = "*") //Permite solicitudes CORS desde cualquier origen
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
	public ResponseEntity<?> guardarCliente(@RequestBody Cliente cliente) {
		boolean existeTelefono = clienteServicio.existeTelefono(cliente.getTelefono());
		
		if (existeTelefono) {
			return ResponseEntity.status(400).body("El numero telefonico ya esta registrado");
		} else {
			boolean existeCliente = clienteServicio.existeCliente(cliente.getNombre(), cliente.getApellidoP(), cliente.getApellidoM());
			if (existeCliente) {
				return ResponseEntity.status(400).body("El cliente ya esta registrado");
			} else {
				clienteServicio.guardar(cliente);
				return ResponseEntity.status(201).body("Cliente guardado con exito");
			}
		}
	}
	
	//Metodo para editar
	@PutMapping("/editar")
	public ResponseEntity<?> editarCliente(@RequestBody Cliente cliente) {
		Cliente existente = clienteServicio.buscar(cliente.getIdCliente());
		if (existente == null) {
			return ResponseEntity.status(404).body("Cliente no encontrado");
		} else {
			clienteServicio.editar(cliente);
			return ResponseEntity.ok("Cliente editado con exito");
		}
	}
	
	//Metodo para buscar
	@GetMapping("/buscar/{idCliente}")
	public ResponseEntity<?> buscar(@PathVariable Integer idCliente){
		Cliente existe = clienteServicio.buscar(idCliente);
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
			return ResponseEntity.status(400).body("Cliente no encontrado");
		}else {
			clienteServicio.eliminar(idCliente);
			return ResponseEntity.ok("Eliminado");
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
