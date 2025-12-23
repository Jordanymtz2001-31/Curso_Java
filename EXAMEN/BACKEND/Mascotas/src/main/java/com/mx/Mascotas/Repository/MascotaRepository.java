package com.mx.Mascotas.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mx.Mascotas.Entity.Mascota;

@Repository //Indica que esta interfaz es un repositorio de Spring Data JPA
public interface MascotaRepository extends JpaRepository<Mascota, Integer>{
	
	//Aqui ya tenemos todos los metodos de un CRUD basico
	
	
	//Metodos Personalizados para buscar por nombre
	Mascota findByNombre(String nombre);
	
	//Metodo para validar si existe una mascota para cuando se edita
	boolean existsByNombreAllIgnoringCase(String nombre);
	
	//Metodo para listar mascotas por cliente
	List<Mascota> findByClienteId(int clienteId);
	//Metodo para listar mascotas por Responsables
	List<Mascota> findByresponsableId(int responsableId);
	//Metodo para lsitar mascotas por Veterinarias
	List<Mascota> findByveterinariaId(int veterinariaId);

}
