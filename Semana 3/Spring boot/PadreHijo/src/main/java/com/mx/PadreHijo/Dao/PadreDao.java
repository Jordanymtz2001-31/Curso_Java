package com.mx.PadreHijo.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mx.PadreHijo.Dominio.Padre;

@Repository //Esto indica que es un componente de acceso a datos
public interface PadreDao extends JpaRepository<Padre, Integer> {
	//Con jpa repository ya tenemos los métodos básicos para CRUD
	
	//METODOS PERSONALIZADOS DE JPA REPOSITORY
	
	//Método para verificar si existe un padre por nombre y apellido, ignorando mayúsculas y minúsculas
	public boolean existsByNombreAndApellidoAllIgnoreCase(String nombre, String apellido);
	//Método para buscar un padre por nombre
	public Padre findByNombre(String nombre);

}
