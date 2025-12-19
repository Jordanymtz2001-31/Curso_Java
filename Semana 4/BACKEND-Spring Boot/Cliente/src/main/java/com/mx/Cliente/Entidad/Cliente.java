package com.mx.Cliente.Entidad;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//Lombok es un proyecto que proporciona anotaciones para reducir el código repetitivo en Java.

@Entity // Indica que esta clase es una entidad de JPA
@Table // Mapea esta entidad a la tabla "clientes" en la base de datos
@Setter // Genera automáticamente los métodos setter
@Getter // Genera automáticamente los métodos getter
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
@ToString // Genera automáticamente el método toString
public class Cliente {
	
	

	@Id // Indica que este campo es la clave primaria
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID_CLIENTE")
	private Integer id;
	private String nombre;
	
	@Column(name="APELLIDO_P")
	private String apellidoP;
	
	@Column(name="APELLIDO_M")
	private String apellidoM;
	
	private Integer edad;
	private Long telefono;
	
	@Column(name="TIENDAD_ID")
	private Integer tiendaId;
	
	
	//Constructor sin idCliente para usarlo en pruebas y al guardar nuevos clientes
	/*
	 * public Cliente(String nombre, String apellidoP, String apellidoM, Integer
	 * edad, Long telefono, Integer tiendaId) {
	 * 
	 * this.nombre = nombre; this.apellidoP = apellidoP; this.apellidoM = apellidoM;
	 * this.edad = edad; this.telefono = telefono; this.tiendaId = tiendaId; }
	 */
	
	
	

}
