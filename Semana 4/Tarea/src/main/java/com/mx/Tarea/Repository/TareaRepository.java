package com.mx.Tarea.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mx.Tarea.Entity.Tareas;

@Repository //Indica que es un repositorio de base de datos
public interface TareaRepository extends JpaRepository<Tareas, Integer> {
	
	//Aqui ya tenemos los metodos CRUD gracias a JpaRepository

}
