package com.mx.Departamento.Entidad;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity // Indica que esta clase es una entidad de JPA
@Table // Indica que esta entidad se mapeará a una tabla en la base de datos
@Data // Genera automáticamente getters, setters, toString, equals y hashCode
public class Departamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idDepartamento;
	private String codigo;
	private String nombre;
	private String descripcion;
	private Long telefono;
	private String email;
	private int tiendaId;
}
