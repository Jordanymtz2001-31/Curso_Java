package com.mx.PadreHijo.Dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity //Definimos la clase Hijo, JPA la manejará como una entidad
@Table
public class Hijo {
	
	@Id //Definimos la llave primaria
	private Integer idHijo;
	private String nombre;
	private String apellido;
	private Integer edad;
	private String hobbie;
	
	@ManyToOne(fetch = FetchType.EAGER) //Definimos la relación muchos a uno y con EAGER cargamos la información del padre junto con el hijo
	@JoinColumn(name = "PADRE_ID") //Especificamos la columna de la llave foránea
	private Padre idPadre; //Colocamos la llave foránea
	
	//Constructores
	public Hijo() {
		super();
	}
	
	public Hijo(Integer idHijo, String nombre, String apellido, Integer edad, String hobbie, Padre idPadre) {
		super();
		this.idHijo = idHijo;
		this.nombre = nombre;
		this.apellido = apellido;
		this.edad = edad;
		this.hobbie = hobbie;
		this.idPadre = idPadre;
	}

	//Getters y Setters
	public Integer getIdHijo() {
		return idHijo;
	}

	public void setIdHijo(Integer idHijo) {
		this.idHijo = idHijo;
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

	public String getHobbie() {
		return hobbie;
	}

	public void setHobbie(String hobbie) {
		this.hobbie = hobbie;
	}

	public Padre getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(Padre idPadre) {
		this.idPadre = idPadre;
	}
	
	//toString
	@Override
	public String toString() {
		return "Hijo [idHijo=" + idHijo + ", nombre=" + nombre + ", apellido=" + apellido + ", edad=" + edad
				+ ", hobbie=" + hobbie + ", idPadre=" + idPadre + "]";
	}
	
	

}
