package com.mx.Tienda.Dtos;

import lombok.Data;

@Data
public class ClienteDto {
	
	private Integer id;
	private String nombre;
	private String apellidoP;
	private String apellidoM;
	private Integer edad;
	private Long telefono;
	private Integer tiendaId;

}
