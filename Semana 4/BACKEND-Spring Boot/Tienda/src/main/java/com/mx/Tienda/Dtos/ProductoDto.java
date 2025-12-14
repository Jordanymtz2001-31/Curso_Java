package com.mx.Tienda.Dtos;

import lombok.Data;

@Data
public class ProductoDto {
	
	private int id;
	private String nombre;
	private int cantidad;
	private String color;
	private double precio;
	private int depaId;
	private int tiendaId;
	
}
