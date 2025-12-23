package com.mx.Cliente.Dto;


import lombok.Data;

@Data //Generea los getter, setter, construtores en automatico
public class MascotaDto {
	
	private int idMascota;
	private String nombre;
	private String raza;
	private int edad;
	private String razonCita;
	private int clienteId; 
	private int responsableId;
	private int veterinariaId;

}
