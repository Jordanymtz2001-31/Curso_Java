package com.mx.validacion.Dto;

import lombok.Data;

//Aqui iran los atributos que vamos a enviar como respuesta
@Data
public class TransaccionResponse {
	
	private Long id;
	private String estatus;
	private String referencia;
	private String operacion;
}
