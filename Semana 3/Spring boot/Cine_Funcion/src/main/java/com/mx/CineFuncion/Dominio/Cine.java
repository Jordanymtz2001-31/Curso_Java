package com.mx.CineFuncion.Dominio;



import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity // Indica que esta clase es una entidad de JPA
@Table// Mapea esta entidad a la tabla "Cine" en la base de datos
public class Cine {
	
	@Id
	private Integer idCine; // Llave primaria de la entidad Cine
	private String nombre;
	private String ciudad;
	private Integer sala;
	private Integer tipo;
	
	//Un cine puede tener muchas funciones
	@OneToMany(mappedBy = "idCine", cascade = CascadeType.ALL)
	List<Funcion> listaFunciones = new ArrayList<>(); // Lista de funciones asociadas al cine
	
	// Constructores
	public Cine() {
		super();
	}
	
	public Cine(Integer idCine, String nombre, String ciudad, Integer sala, Integer tipo) {
		super();
		this.idCine = idCine;
		this.nombre = nombre;
		this.ciudad = ciudad;
		this.sala = sala;
		this.tipo = tipo;
	}
	
	// Getters y Setters
	public Integer getIdCine() {
		return idCine;
	}

	public void setIdCine(Integer idCine) {
		this.idCine = idCine;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public Integer getSala() {
		return sala;
	}

	public void setSala(Integer sala) {
		this.sala = sala;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "Cine [idCine=" + idCine + ", nombre=" + nombre + ", ciudad=" + ciudad + ", sala=" + sala + ", tipo="
				+ tipo + "]";
	}
	
	
}
