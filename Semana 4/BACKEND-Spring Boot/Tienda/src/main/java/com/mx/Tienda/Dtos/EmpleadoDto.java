package com.mx.Tienda.Dtos;

import lombok.Data;

@Data
public class EmpleadoDto {
	
	private int id;
	private String nombre;
	private String apellidoP;
	private String apellidoM;
	private int edad;
	private Long telefono;
	private String puesto;
	private int departamentoId;
	private int tiendaId;

}
