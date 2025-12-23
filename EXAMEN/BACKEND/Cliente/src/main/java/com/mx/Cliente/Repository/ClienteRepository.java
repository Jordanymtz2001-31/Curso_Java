package com.mx.Cliente.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mx.Cliente.Entity.Cliente;

@Repository 
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	
	//Con JPARepository ya tenemos por default los metodos de un DRUD
	
		//Metodo personalizados
		//Buscar Responsable por nombre
		Cliente findByNombre(String nombre);
		
		//Metodo para validar si existe el responsable por su nombre
		boolean existsByNombreAllIgnoringCase(String nombre);
		
		//Metodo para vlidar si existe el contacto
		boolean existsByContacto(Long contacto); 
		
		//Metodo para validar si ya existe la direccion
		boolean existsByDireccionAllIgnoringCase(String direccion);
		
		

}
