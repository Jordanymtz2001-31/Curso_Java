package com.mx.Veterinaria.Dtos;

import lombok.Data;

@Data
public class ResponsableDto {

	private Integer idResponsable;
	private String nombre;
	private Long contacto;
	private int veterinariaId;

}
