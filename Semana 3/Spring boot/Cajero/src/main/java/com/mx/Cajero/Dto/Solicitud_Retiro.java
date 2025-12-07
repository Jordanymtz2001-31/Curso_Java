package com.mx.Cajero.Dto;

//Case para almacenar la solicitud de retiro por el cliente
public class Solicitud_Retiro {
	
	private Float montoRetiro;

	//Constructores
	public Solicitud_Retiro() {
		super();
	}

	public Solicitud_Retiro(Float montoRetiro) {
		super();
		this.montoRetiro = montoRetiro;
	}
	
	//Getters y Setters

	public Float getMontoRetiro() {
		return montoRetiro;
	}

	public void setMontoRetiro(Float montoRetiro) {
		this.montoRetiro = montoRetiro;
	}

	@Override
	public String toString() {
		return "Solicitud_Retiro [montoRetiro=" + montoRetiro + "]";
	}
	
	

}
