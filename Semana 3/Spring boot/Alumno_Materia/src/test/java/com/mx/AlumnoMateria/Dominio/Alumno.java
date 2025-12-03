package com.mx.AlumnoMateria.Dominio;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity //Definimos la clase Alumno, JPA la manejará como una entidad
@Table //Definimos la tabla Alumno
public class Alumno {
	
	@Id
	private Integer idAlumno;
	private String nombre;
	private String carrera;
	private Integer semestre;
	
	//Definimos que un alumno puede tener muchas materias
	@OneToMany(mappedBy = "idAlumno", cascade = CascadeType.ALL)
	List<Materia> lista = new ArrayList<>(); //Lista de materias asociadas al alumno
	
	//Constructores
	public Alumno() {
		super();
	}
	
	public Alumno(Integer idAlumno, String nombre, String carrera, Integer semestre) {
		super();
		this.idAlumno = idAlumno;
		this.nombre = nombre;
		this.carrera = carrera;
		this.semestre = semestre;
	}
	
	//Getters y Setters
	public Integer getIdAlumno() {
		return idAlumno;
	}

	public void setIdAlumno(Integer idAlumno) {
		this.idAlumno = idAlumno;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCarrera() {
		return carrera;
	}

	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

	public Integer getSemestre() {
		return semestre;
	}

	public void setSemestre(Integer semestre) {
		this.semestre = semestre;
	}

	@Override
	public String toString() {
		return "Alumno [idAlumno=" + idAlumno + ", nombre=" + nombre + ", carrera=" + carrera + ", semestre=" + semestre
				+ "]";
	}
	
	

}
