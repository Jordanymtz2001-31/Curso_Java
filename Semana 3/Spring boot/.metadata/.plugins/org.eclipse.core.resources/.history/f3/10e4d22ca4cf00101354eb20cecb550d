package com.mx.PadreHijo.Dominio;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity //Definimos la clase Padre, JPA la manejará como una entidad
@Table
public class Padre {

	@Id //Definimos la llave primaria
	private Integer idPadre;
	private String nombre; 
	private String apellido; 
	private Integer edad; 
	private String trabajo; 
	
	@OneToMany(mappedBy = "idPadre", cascade = CascadeType.ALL) //Definimos la relación uno a muchos, especificamos el atributo que mapea la relación en la clase Hijo
	List<Hijo> lista = new ArrayList<>(); //Lista de hijos asociados al padre
	
	//Constructores
	public Padre() {
		super();
	}
	
	public Padre(Integer idPadre, String nombre, String apellido, Integer edad, String trabajo) {
		super();
		this.idPadre = idPadre;
		this.nombre = nombre;
		this.apellido = apellido;
		this.edad = edad;
		this.trabajo = trabajo;
	}

	//Getters y Setters
	public Integer getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(Integer idPadre) {
		this.idPadre = idPadre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public String getTrabajo() {
		return trabajo;
	}

	public void setTrabajo(String trabajo) {
		this.trabajo = trabajo;
	}
	
	//toString
	
	@Override
	public String toString() {
		return "Padre [idPadre=" + idPadre + ", nombre=" + nombre + ", apellido=" + apellido + ", edad=" + edad
				+ ", trabajo=" + trabajo + "]";
	}
	
	
	
}
