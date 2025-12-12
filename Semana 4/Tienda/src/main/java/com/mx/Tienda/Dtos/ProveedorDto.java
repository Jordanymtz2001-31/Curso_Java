package com.mx.Tienda.Dtos;

import lombok.Data;

@Data
public class ProveedorDto {
	
	private int idProveedor;

	private String nombreProveedor;
	private String tipo;
	private String responsable; //Persona responsable
	private Long telefono;
	private String direccion;
	private String email;
	private int tiendaId;

}
