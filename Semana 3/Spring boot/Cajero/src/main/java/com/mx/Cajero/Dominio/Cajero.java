package com.mx.Cajero.Dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity //Definimos la clase Cajero, JPA la manejará como una entidad
@Table(name="CAJERO") //Definimos la tabla Cajero
public class Cajero {
	
	@Id //Definimos la llave primaria
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que el valor del campo se generará automáticamente
	private Integer idDinero;
	private String tipo;
	private Integer cantidad;
	private Float denominacion;
	
	//Constructores
	public Cajero() {
		super();
	}
	
	public Cajero(Integer idDinero, String tipo, Integer cantidad, Float denominacion) {
		super();
		this.idDinero = idDinero;
		this.tipo = tipo;
		this.cantidad = cantidad;
		this.denominacion = denominacion;
	}
	
	//Getters y Setters	
	public Integer getIdDinero() {
		return idDinero;
	}

	public void setIdDinero(Integer idDinero) {
		this.idDinero = idDinero;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Float getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(Float denominacion) {
		this.denominacion = denominacion;
	}
	
	//toString
	@Override
	public String toString() {
		return "Cajero [idDinero=" + idDinero + ", tipo=" + tipo + ", cantidad=" + cantidad + ", denominacion="
				+ denominacion + "]";
	}
	
	
	
}
