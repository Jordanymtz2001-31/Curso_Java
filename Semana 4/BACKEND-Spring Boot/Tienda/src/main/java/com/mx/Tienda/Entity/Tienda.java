package com.mx.Tienda.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity // Indica que es una entidad
@Table //Indica que es una tabla 
@Data // Genera los getters y setters
public class Tienda {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idTienda;
	private String codigo;
	private String nombre;
	private String direccion;
	private Etipos tipo;
	private String ciudad;
	
	//Contructor para las prueba y no pasarle el ID por que ya se genera
	public Tienda(String codigo, String nombre, String direccion, Etipos tipo, String ciudad) {
		
		this.codigo = codigo;
		this.nombre = nombre;
		this.direccion = direccion;
		this.tipo = tipo;
		this.ciudad = ciudad;
	}
	
	

}
