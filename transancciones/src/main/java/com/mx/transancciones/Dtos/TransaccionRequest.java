package com.mx.transancciones.Dtos;

import lombok.Data;

// DTO para manejar las solicitudes de transaccion 
@Data
public class TransaccionRequest {
	
	private String operacion;
	private String importe;
	private String cliente;
	private String sha;

	
	
}
