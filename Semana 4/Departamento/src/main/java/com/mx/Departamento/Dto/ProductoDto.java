package com.mx.Departamento.Dto;

import lombok.Data;

@Data // Genera automáticamente getters, setters, toString, equals y hashCode
public class ProductoDto {
	private int id;
	private String nombre;
	private int cantidad;
	private String color;
	private double precio;
	private int depaId;
	private int tiendaId;

}
