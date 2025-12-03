package com.mx.Cine.Dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mx.Cine.Dominio.Cine;

@Repository 
public interface CineDao extends CrudRepository<Cine, Integer> {

	//Esta es la capa de acceso a datos (DAO) para la entidad Empleado.
	//Con esta interfaz, Spring Data JPA proporcionará automáticamente
	//implementaciones CRUD básicas para la entidad Empleado.
}
