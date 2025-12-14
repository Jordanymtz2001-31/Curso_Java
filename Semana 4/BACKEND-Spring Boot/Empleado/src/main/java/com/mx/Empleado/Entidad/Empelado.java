package com.mx.Empleado.Entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data // Genera automáticamente getters, setters, toString, equals y hashCode
public class Empelado {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Indica que el id se genera automáticamente
	private int id;
	private String nombre;
	private String apellidoP;
	private String apellidoM;
	private int edad;
	
	@Column(nullable= false, unique = true)
	private Long telefono;
	private Epuesto puesto;
	private int departamentoId;
	private int tiendaId;

}
