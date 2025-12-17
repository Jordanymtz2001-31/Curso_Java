package com.mx.validacion.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.mx.validacion.Dto.OperacionesRequest;
import com.mx.validacion.Dto.TransaccionRequest;
import com.mx.validacion.Dto.TransaccionResponse;
import com.mx.validacion.Service.OperacionesService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/operacion")
public class OperacionController {
	
	//Hacemos la inyecccion del servicio y de RestTemplate
	@Autowired 
	private OperacionesService shaService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	//Creamos el metodo para procesar la oprecion
	@PostMapping
	public ResponseEntity<TransaccionResponse> procesarOp(@Valid @RequestBody OperacionesRequest solicitud){
		
		//Validamos el sha que coincida con el calculado
		String shaCalculado = shaService.calcularSha(solicitud.getOperacion(), solicitud.getImporte(), solicitud.getCliente());
		
		System.out.println("SHA Calculado: " + shaCalculado);
		System.out.println("SHA Recibido: " + solicitud.getSha());
		
		if(!shaCalculado.equalsIgnoreCase(solicitud.getSha())) {
			//Si es diferente entonces mandamos un mensaje de error
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
		//Si el sha es correcto, entonces enviamos la solicitud al servicio de transsaciones
		//Creamos el body de la solicitud
		TransaccionRequest body = new TransaccionRequest();
		body.setOperacion(solicitud.getOperacion());
		body.setImporte(solicitud.getImporte());
		body.setCliente(solicitud.getCliente());
		//Pasamos el el sha calculado
		
		
		String url = "http://localhost:8000/transacciones";
		
		TransaccionResponse resp = restTemplate.postForObject(url, body, TransaccionResponse.class);
		return ResponseEntity.ok(resp);
		
	}
	
	//Endpoint auxiliar para calcular el SHA sin hacer validacion completa
	@PostMapping("/calcular-sha")
	public ResponseEntity<String> calcularSha(@RequestBody OperacionesRequest solicitud){
		String shaCalculado = shaService.calcularSha(solicitud.getOperacion(), solicitud.getImporte(), solicitud.getCliente());
		return ResponseEntity.ok(shaCalculado);
	}}
