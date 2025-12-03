package com.mx.Computadora.Dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // Indica que esta clase es una entidad de JPA
@Table // Indica que esta entidad se mapeará a una tabla en la base de datos
public class Computadora {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que el valor del campo se generará automáticamente
	private Integer idCompu;
	private String marca;
	private String modelo;
	private Integer ram;
	private String procesador;
	private Double precio;
	
	//Constructores
	public Computadora() {
		super();
	}
	
	public Computadora(Integer idCompu, String marca, String modelo, Integer ram, String procesador,
			Double precio) {
		super();
		this.idCompu = idCompu;
		this.marca = marca;
		this.modelo = modelo;
		this.ram = ram;
		this.procesador = procesador;
		this.precio = precio;
	}

	public Integer getIdCompu() {
		return idCompu;
	}

	public void setIdCompu(Integer idCompu) {
		this.idCompu = idCompu;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public Integer getRam() {
		return ram;
	}

	public void setRam(Integer ram) {
		this.ram = ram;
	}

	public String getProcesador() {
		return procesador;
	}

	public void setProcesador(String procesador) {
		this.procesador = procesador;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "Computadora [idCompu=" + idCompu + ", marca=" + marca + ", modelo=" + modelo + ", ram=" + ram
				+ ", procesador=" + procesador + ", precio=" + precio + "]";
	}
	
	

}
