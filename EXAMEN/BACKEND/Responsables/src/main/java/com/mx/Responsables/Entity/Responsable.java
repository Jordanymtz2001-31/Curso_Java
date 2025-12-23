package com.mx.Responsables.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table //Indicamos que es una tabla 
@Entity //Indicamos que es una entidad
@Data // Genera getters, setters, toString, equals, hashCode y constructor requerido
@NoArgsConstructor // Constructor sin argumentos requerido por JPA
public class Responsable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Generados de numero automaricamente
	private Integer idResponsable;
	private String nombre;
	private Long contacto;
	private int veterinariaId;

}
