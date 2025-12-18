package com.mx.Computadora.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mx.Computadora.Dominio.Computadora;

@Repository // Indica que esta interfaz es un repositorio de Spring Data JPA
public interface ComputadoraDao extends JpaRepository<Computadora, Integer> {
	
	// Cosumo del procedimiento almacenado APLICAR_DESCUENTO
	@Procedure(procedureName = "APLICAR_DESCUENTO") // Nombre del procedimiento almacenado de la base de datos
	void aplicarDescuento(@Param("P_DESCUENTO") Integer pDescuento); // Parámetro de entrada del procedimiento almacenado
	
	public boolean existsByMarcaAndModeloAllIgnoringCase(String marca, String modelo);

}
