package com.mx.Producto.Entidad;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity // Indica que esta clase es una entidad JPA
@Table(name = "producto") // Indica que esta clase se mapeará a una tabla en la base de datos
@Data // Genera automáticamente getters, setters, toString, equals y hashCode
public class Producto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que el ID se generará automáticamente
	private int id;
	private String nombre;
	private int cantidad;
	private String color;
	private double precio;
	private int depaId;
	private int tiendaId;
	

}
