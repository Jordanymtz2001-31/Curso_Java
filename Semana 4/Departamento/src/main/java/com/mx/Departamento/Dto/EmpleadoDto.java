package com.mx.Departamento.Dto;

import lombok.Data;

@Data // Genera automáticamente getters, setters, toString, equals y hashCode
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
