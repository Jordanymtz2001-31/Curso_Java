package com.mx.Cajero.Dto;

import java.util.List;

//Clase para almacenar la respuesta del retiro
public class Respuesta_Retiro {
	private boolean exito;
	private Float montoEntregado;
	private List<DenominacionesEntregadas> denominaciones;
	private Float saldoRestante;
	
	//Constructores
	public Respuesta_Retiro() {
		super();
	}
	
	public Respuesta_Retiro(boolean exito, Float montoEntregado, List<DenominacionesEntregadas> denominaciones,
			float saldoRestante) {
		super();
		this.exito = exito;
		this.montoEntregado = montoEntregado;
		this.denominaciones = denominaciones;
		this.saldoRestante = saldoRestante;
	}

	//Getters y Setters
	public boolean isExito() {
		return exito;
	}

	public void setExito(boolean exito) {
		this.exito = exito;
	}

	public Float getMontoEntregado() {
		return montoEntregado;
	}

	public void setMontoEntregado(Float montoEntregado) {
		this.montoEntregado = montoEntregado;
	}

	public List<DenominacionesEntregadas> getDenominaciones() {
		return denominaciones;
	}

	public void setDenominaciones(List<DenominacionesEntregadas> denominaciones) {
		this.denominaciones = denominaciones;
	}

	public Float getSaldoRestante() {
		return saldoRestante;
	}

	public void setSaldoRestante(Float saldoRestante) {
		this.saldoRestante = saldoRestante;
	}

	@Override
	public String toString() {
		return "Respuesta_Retiro [exito=" + exito + ", montoEntregado=" + montoEntregado + ", denominaciones="
				+ denominaciones + ", saldoRestante=" + saldoRestante + "]";
	}
	
	

}
