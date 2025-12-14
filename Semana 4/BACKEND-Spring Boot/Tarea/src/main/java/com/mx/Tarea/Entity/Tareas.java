package com.mx.Tarea.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity //Indica que es una entidad de base de datos
@Table //Indica el nombre de la tabla en la base de datos
@Data //Genera los getters y setters automaticamente
public class Tareas {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Indica que es una clave primaria y se genera automaticamente
	private int idTarea;
	
	@Column(nullable = false)
	private String titulo;
	@Column(nullable = false)
	private String descripcion;
	private LocalDate fechaCreacion;
	private LocalDate fechaVencimiento;
	private Eestado estado;

}
