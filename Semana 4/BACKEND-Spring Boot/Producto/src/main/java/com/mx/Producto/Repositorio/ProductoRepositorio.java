package com.mx.Producto.Repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mx.Producto.Entidad.Producto;

@Repository // Indica que esta interfaz es un repositorio de Spring Data JPA
public interface ProductoRepositorio extends JpaRepository<Producto, Integer> {
	// JpaRepository proporciona métodos CRUD y de paginación para la entidad Producto
	
	//Metodo para validar si exite un producto por su nombre
	boolean existsByNombreAllIgnoringCase(String nombre);
	
	//Metodo para listar productos por departamentoId
	List<Producto> findByDepaId(int depaId);
	
	//Metodo para listar productos por tiendaId
	List<Producto> findByTiendaId(int tiendaId);

}
