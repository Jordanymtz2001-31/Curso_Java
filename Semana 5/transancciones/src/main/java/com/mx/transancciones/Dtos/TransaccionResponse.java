package com.mx.transancciones.Dtos;

import lombok.Data;

@Data // DTO para manejar las respuestas de transaccion
public class TransaccionResponse {
	
	private Long id;
	private String estatus;
	private String referencia;
	private String oprecacion;
	private String sha;

}
