package com.mx.AlumnoMateria.Dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity //Definimos la clase Materia, JPA la manejará como una entidad
@Table //Definimos la tabla Materia
public class Materia {
	
	@Id
	private Integer idMateria;
	private String nombre;
	private Integer creditos;
	
	//Muchas materias pueden pertenecer a un alumno
	@ManyToOne(fetch = FetchType.EAGER) //El EAGER carga la información del alumno junto con la materia
	@JoinColumn(name = "ALUMNO_ID") //Especificamos la columna de la llave foránea
	
	//Constructores
	public Materia() {
		super();
	}
	
	public Materia(Integer idMateria, String nombre, Integer creditos) {
		super();
		this.idMateria = idMateria;
		this.nombre = nombre;
		this.creditos = creditos;
	}
	
	//Getters y Setters
	public Integer getIdMateria() {
		return idMateria;
	}

	public void setIdMateria(Integer idMateria) {
		this.idMateria = idMateria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getCreditos() {
		return creditos;
	}

	public void setCreditos(Integer creditos) {
		this.creditos = creditos;
	}

	@Override
	public String toString() {
		return "Materia [idMateria=" + idMateria + ", nombre=" + nombre + ", creditos=" + creditos + "]";
	}

}
