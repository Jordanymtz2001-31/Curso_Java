package com.mx.Mascotas.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity //Indica que es una entidad
@Table //Indica que es una tabla
@Data //Generea los getter, setter, construtores en automatico
public class Mascota {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Genera el autoincrementable del ID
	private int idMascota;
	private String nombre;
	private String raza;
	private int edad;
	private String razonCita;
	private int clienteId; 
	private int responsableId;
	private int veterinariaId;

}
