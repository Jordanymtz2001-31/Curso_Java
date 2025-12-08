package com.mx.Cajero.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.Cajero.Dto.Solicitud_Retiro;
import com.mx.Cajero.Service.CajeroServices;

@RestController //Indicamos que esta clase es un controlador REST
@RequestMapping("/cajero") //Definimos la ruta base para este controlador
@CrossOrigin(origins = "*") //Habilitamos CORS para permitir solicitudes desde otros dominios
public class CajeroController {
	
	@Autowired //Inyeccion de dependencia del servicio
	private CajeroServices servicio;
	
	//Metodo para retirar dinero
	@PostMapping("/retirar")
	//@RequestBody indica que el parametro se obtiene del cuerpo de la solicitud HTTP
	// y Solicitud_Retiro es el DTO que contiene el monto a retirar
	public ResponseEntity<?> retirarDinero(@RequestBody Solicitud_Retiro motoRetiro) {
	    try {//Validamos que el monto no sea nulo
	        if (motoRetiro.getMontoRetiro() == null) {
	            return ResponseEntity.badRequest().body("El monto no puede ser nulo");
	        }
	        return ResponseEntity.ok(servicio.procesoRetiro((motoRetiro.getMontoRetiro())));
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	    }
	}
	
	//Metodo de reinicializacion del cajero
	@PostMapping("/reiniciar")
	public ResponseEntity<?> reiniciarCajero() {
		servicio.reiniciarCajero();
		return ResponseEntity.ok("Cajero reiniciado correctamente.");
	}
	
	//Metodo para consultar el saldo del cajero
	@GetMapping("/saldo")
	public Float consultarSaldo() {
		return servicio.obtenerSaldoCajero();
	}
	
	//Metodo para consultar el inventario del cajero
	@GetMapping("/inventario")
	public ResponseEntity<?> consultarInventario() {
		return ResponseEntity.ok(servicio.obtenerInventarioCajero());
	}

}
