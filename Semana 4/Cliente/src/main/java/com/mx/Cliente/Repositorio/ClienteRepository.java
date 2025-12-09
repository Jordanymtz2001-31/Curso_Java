package com.mx.Cliente.Repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mx.Cliente.Entidad.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	//Meto para verificar si existe por su nombre y apellido paterno y materno
	boolean existsByNombreAndApellidoPAndApellidoMAllIgnoringCase(String nombre, String apellidoP, String apellidoM );
	
	//Metodo para listar clientes por tienda
	public List<Cliente> findByTiendaId(int tiendaId);
	
	//Metodo para verificar que no aya numero telefonico repetido
	boolean existsByTelefono(Long telefono);
}
