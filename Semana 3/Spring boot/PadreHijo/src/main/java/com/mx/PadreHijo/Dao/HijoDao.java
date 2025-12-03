package com.mx.PadreHijo.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mx.PadreHijo.Dominio.Hijo;

@Repository //Esto indica que es un componente de acceso a datos
public interface HijoDao extends JpaRepository<Hijo, Integer> {
	//Con JPA repository ya tenemos los métodos básicos para CRUD
	
	//Anotacion que nos permite hacer consultas personalizadas con SQL
	@Query("SELECT h FROM Hijo h WHERE upper(h.hobbie) = upper(:hobbie)")
	List<Hijo> findByHobbie(@Param("hobbie") String hobbie);

}
