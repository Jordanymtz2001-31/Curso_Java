package com.mx.validacion.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import org.springframework.stereotype.Service;

@Service
public class OperacionesService {
	
	//Calcularemos aqui el Sha
	//Generandolo con los demas atributos 
	public String calcularSha(String operacion, String importe, String cliente) {
		try {
			String data = operacion + importe + cliente;
			
			//Ocupamos la clase MessageDigest para generar el sha 
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			
			//Generamos el digest 
			//Convertimos la cadena a bytes para despues generar el sha 
			//Usamos el StandardCharsets para evitar problemas de codificacion
			byte[] digitos = md.digest(data.getBytes(StandardCharsets.UTF_8));
			
			//Convertimos los bytes a una cadena hexadecimal
			StringBuilder sb = new StringBuilder();
			for (byte b : digitos ) {
				sb.append(String.format("%02x", b)); //Se va agregando cada byte en formato hexadecimal
			}
			//Retornamos el sha generado
			return sb.toString();
		}catch (Exception e) {
			throw new RuntimeException("Error generando SHA-512", e);
		}
	}

}
