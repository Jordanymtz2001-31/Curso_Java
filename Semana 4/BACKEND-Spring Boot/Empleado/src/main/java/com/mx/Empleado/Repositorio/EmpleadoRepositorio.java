package com.mx.Empleado.Repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mx.Empleado.Entidad.Empelado;

@Repository // Indica que esta interfaz es un repositorio de Spring Data JPA
public interface EmpleadoRepositorio extends JpaRepository<Empelado, Integer> {
	// JpaRepository proporciona métodos CRUD y de paginación para la entidad Empelado
	
	//Metodo para validar si exite un empleado por su nombre, apellidoP y apellidoM
	boolean existsByNombreAndApellidoPAndApellidoMAllIgnoringCase(String nombre, String apellidoP, String apellidoM);
	
	//Metodo para validar si existe el numero de telefono
	boolean existsByTelefono(Long telefono);
	
	//Metodo para listar empleados por departamentoId
	List<Empelado> findByDepartamentoId(int departamentoId);
	
	//Metodo para listar empleados por tiendaId
	List<Empelado> findByTiendaId(int tiendaId);	

}
