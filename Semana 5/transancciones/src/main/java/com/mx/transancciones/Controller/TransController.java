package com.mx.transancciones.Controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.transancciones.Dtos.TransaccionRequest;
import com.mx.transancciones.Dtos.TransaccionResponse;
import com.mx.transancciones.Entity.Transaccion;
import com.mx.transancciones.Service.TransaccionesService;

@RestController
@RequestMapping("/transacciones")
@CrossOrigin
public class TransController {
	
	@Autowired
	private TransaccionesService service;
	
	
	@PostMapping
	public ResponseEntity<TransaccionResponse> guardar(@RequestBody TransaccionRequest request){
		return ResponseEntity.ok(service.guardar(request));
	}
	
	@GetMapping
	public ResponseEntity<List<Transaccion>> listar(){
		return ResponseEntity.ok(service.listar());
	}

 
}
