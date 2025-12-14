package com.mx.Tienda.Dtos;

import lombok.Data;

@Data
public class DepartamentoDto {
	
	private int idDepartamento;
	private String codigo;
	private String nombre;
	private String descripcion;
	private Long telefono;
	private String email;
	private int tiendaId;

}
