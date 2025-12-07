package com.mx.Cajero.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mx.Cajero.Dominio.Cajero;

@Repository //Definimos la interfaz como un repositorio de Spring Data JPA
public interface CajeroDao extends JpaRepository<Cajero, Integer> {
	
	//Usaremos los métodos CRUD que nos proporciona JpaRepository
	
	//Metodo para listar las denominaciones ordenados de mayor a menor 
	List<Cajero> findAllByOrderByDenominacionDesc();
    
  
}
