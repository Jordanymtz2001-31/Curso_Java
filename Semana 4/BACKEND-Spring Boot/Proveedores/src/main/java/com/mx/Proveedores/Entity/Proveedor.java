package com.mx.Proveedores.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data // Genera automáticamente getters, setters, toString, equals y hashCode
@Table // Indica que esta clase es una entidad de base de datos
@Entity // Marca la clase como una entidad JPA donde se mapeará una tabla
public class Proveedor {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que el campo es una clave primaria y su valor se genera automáticamente
	private int idProveedor;

	@Column(nullable = false, unique = true)
	private String nombreProveedor;
	private String tipo;
	private String responsable; //Persona responsable

	@Column(nullable = false, unique = true)
	private Long telefono;

	@Column(nullable = false, unique = true)
	private String direccion;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private int tiendaId;

}
