package com.mx.validacion.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class OperacionesRequest {
	
	//Aqui iran todos los atributos que vamos a recibir para validar
	
	@NotBlank //Anotacio para validar que no venga vacio
	@Pattern(regexp = "^[A-Za-z]+$", message = "Solo letras") // Anotacion para que solo ingrese letras	
	private String operacion;
	
	@NotBlank //Anotacion para validar que no venga vacio
    // expresión simple tipo moneda: 2 decimales o un número entero
    @Pattern(regexp = "^[0-9]+(\\.[0-9]{1,2})?$", message = "Formato de importe inválido (ej: 100 o 100.50)")
	private String importe;
	
	@NotBlank //Anotacion para validar que no venga vacio
	@Pattern(regexp = "^[A-Za-z]+$", message = "Solo letras") // Anotacion para que solo ingrese letras	
	private String cliente;
	
	// Variable que sera utilizada para la validacion de integridad
	@NotBlank //Anotacion para validar que no venga vacio
	private String sha;
	

}
