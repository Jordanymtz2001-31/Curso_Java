package com.mx.transancciones.Respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mx.transancciones.Entity.Transaccion;

@Repository //Indicamos que es un repositorio
public interface TransaccionRepository extends JpaRepository<Transaccion, Long>{
	
	//Aqui ya tenemos los emtodos CRUD basicos

}
