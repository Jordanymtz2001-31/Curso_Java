package com.mx.Proveedores.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mx.Proveedores.Entity.Proveedor;

@Repository // Indica que esta interfaz es un repositorio de Spring Data JPA
public interface ProveedorRepo extends JpaRepository<Proveedor, Integer> {

    //Aqui ya nos ofres el CRUD completo con JpaRepository

	//Listar todos los proveedores por tiendaId
	List<Proveedor> findByTiendaId(int tiendaId);

	//Validamos si existe un proveedor por nombreProveedor
	boolean existsByNombreProveedorAllIgnoringCase(String nombreProveedor);

	//Validamos si existe un proveedor por Direccion
	boolean existsByDireccionAllIgnoringCase(String direccion);

	//Validamos si existe un proveedor por Email
	boolean existsByEmailAllIgnoringCase(String email);

	//Validamos si existe un proveedor por Telefono
	boolean existsByTelefono(Long telefono);

}
