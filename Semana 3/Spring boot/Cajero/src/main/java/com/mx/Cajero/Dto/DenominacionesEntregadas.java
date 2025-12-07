package com.mx.Cajero.Dto;

//Clase para almacenar las denominaciones entregadas en el retiro
public class DenominacionesEntregadas {
	private Float denominacion;
	private Integer cantidad;
	
	//Constructores
	public DenominacionesEntregadas() {
		super();
	}

	public DenominacionesEntregadas(Float denominacion, Integer cantidad) {
		super();
		this.denominacion = denominacion;
		this.cantidad = cantidad;
	}

	//Getters y Setters
	public Float getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(Float denominacion) {
		this.denominacion = denominacion;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "DenominacionesEntregadas [denominacion=" + denominacion + ", cantidad=" + cantidad + "]";
	}
	
	

}
