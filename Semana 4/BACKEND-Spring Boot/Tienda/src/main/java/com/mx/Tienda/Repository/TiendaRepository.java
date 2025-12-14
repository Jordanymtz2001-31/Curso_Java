package com.mx.Tienda.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mx.Tienda.Entity.Tienda;

@Repository // Indica que es un repositorio 
public interface TiendaRepository extends JpaRepository<Tienda, Integer> {
	
	//Metodo de validar si existe el codigo de la tienda
	boolean existsByCodigoIgnoreCase(String codigo);
	
	//Metodo de validar si existe el nombre de la tienda
	boolean existsByNombreIgnoreCase(String nombre);
	
	//Metodo de validar si existe la direccion de la tienda
	boolean existsByDireccionIgnoreCase(String direccion);
	
	//Metodo para buscar tienda por nombre
	Tienda findByNombre(String nombre);
	
	//Metodo para buscar tiendas por ciudad
	List<Tienda> findByCiudadIgnoreCase(String ciudad);
	
	
}
