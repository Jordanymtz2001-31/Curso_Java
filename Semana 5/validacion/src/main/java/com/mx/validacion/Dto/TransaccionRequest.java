package com.mx.validacion.Dto;

import lombok.Data;

//Aqui iran los atributos que vamos a recibir para validar
@Data
public class TransaccionRequest {
	private String operacion;
	private String importe;
	private String cliente;
	

}
