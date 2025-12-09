package com.mx.Departamento.Repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mx.Departamento.Entidad.Departamento;

@Repository // Indica que esta interfaz es un repositorio de Spring Data JPA
public interface DepartamentoRepo extends JpaRepository<Departamento, Integer> {
	
	//Metodo para lista de departamentos por tienda
	List<Departamento> findByTiendaId(int tiendaId);

}
