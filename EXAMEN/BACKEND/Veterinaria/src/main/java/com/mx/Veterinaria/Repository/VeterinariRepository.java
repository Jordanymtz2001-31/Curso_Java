package com.mx.Veterinaria.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mx.Veterinaria.Entity.Veterinaria;

@Repository // Indica que esta interfaz es un repositorio de Spring Data JPA
public interface VeterinariRepository extends JpaRepository<Veterinaria, Integer>{
	
	//Con JPARepository ya tenemos por default los metodos de un DRUD
	
		//Metodo personalizados
		//Buscar Responsable por nombre
		Veterinaria findByNombre(String nombre);
		
		//Metodo para validar si existe la veterinaria por su nombre
		boolean existsByNombreAllIgnoringCase(String nombre);
		
		//Metodo para vlidar si existe el contacto
		boolean existsByTelefono(Long telefono);
		
		//Metodo para validar si existe la direccion
		boolean existsByDireccionAllIgnoringCase(String direccion);
		

}
