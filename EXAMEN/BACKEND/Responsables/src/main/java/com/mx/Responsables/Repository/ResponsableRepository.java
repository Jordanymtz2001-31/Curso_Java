package com.mx.Responsables.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mx.Responsables.Entity.Responsable;
import java.util.List;

@Repository
public interface ResponsableRepository extends JpaRepository<Responsable, Integer>{
	
	//Con JPARepository ya tenemos por default los metodos de un DRUD
	
	//Metodo personalizados
	//Buscar Responsable por nombre
	Responsable findByNombre(String nombre);
	
	//Metodo para validar si existe el responsable por su nombre
	boolean existsByNombreAllIgnoringCase(String nombre);
	
	//Metodo para vlidar si existe el contacto
	boolean existsByContacto(Long contacto); 
	
	//Metodo para listar responsable por veterinaria ID
	List<Responsable> findByVeterinariaId(int veterinariaId);

}
