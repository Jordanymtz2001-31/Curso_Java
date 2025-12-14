package com.mx.Proveedores.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mx.Proveedores.Entity.Proveedor;
import com.mx.Proveedores.Repository.ProveedorRepo;

@Service // Indica que esta clase es un servicio de Spring
public class ProveedorService {

    
	@Autowired // Inyecta el repositorio de proveedores
	private ProveedorRepo dao;

	//Metodo para validar los campos unicos antes de guardar un proveedor
	public void ValidarCampos(Proveedor proveedor) throws Exception {

		if(existeNombreProveedor(proveedor.getNombreProveedor())) {
			throw new Exception("El nombre del proveedor ya existe");
		}

		if(existeDireccion(proveedor.getDireccion())) {
			throw new Exception("La direccion del proveedor ya existe");
		}

		if(existeEmail(proveedor.getEmail())) {
			throw new Exception("El email del proveedor ya existe");
		}

		if(existeTelefono(proveedor.getTelefono())) {
			throw new Exception("El telefono del proveedor ya existe");
		}
	}

	//Metodo para guardar un proveedor
	public void guardar(Proveedor proveedor) throws Exception  {
		ValidarCampos(proveedor); // Validar los campos unicos antes de guardar
		try {
			dao.save(proveedor);
		} catch (Exception e) {
			// Manejar la excepción según sea necesario
			throw new RuntimeException("Error al guardar el proveedor: " + e.getMessage(), e);
		}
	}

	//Metodo para editar un proveedor
	public void editar(Proveedor proveedor) throws Exception  {
		//No se validan los campos unicos al editar
		try {
			dao.save(proveedor);
		} catch (Exception e) {
			// Manejar la excepción según sea necesario
			throw new RuntimeException("Error al editar el proveedor: " + e.getMessage(), e);
		}
	}

	//Metodo para eliminar un proveedor
	public void eliminar(int idProveedor) {
		dao.deleteById(idProveedor);
	}

	//Metodo para buscar un proveedor por id
	public Proveedor buscar(int idProveedor) {
		return dao.findById(idProveedor).orElse(null);
	}

	//Metodo para listar todos los proveedores
		public List<Proveedor> listar() {
			return dao.findAll(Sort.by(Sort.Direction.ASC, "nombreProveedor"));
		}

	//----------------------------------------------------------------------------------------------------


	//Metodo para listar los proveedores por tiendaId
	public List<Proveedor> listarPorTiendaId(int tiendaId) {
		return dao.findByTiendaId(tiendaId);
	}


	//Metodo de validacion de existencia por nombreProveedor
	public boolean existeNombreProveedor(String nombreProveedor) {
		return dao.existsByNombreProveedorAllIgnoringCase(nombreProveedor);
	}

	//Metodo de validacion de existencia por Direccion
	public boolean existeDireccion(String direccion) {
		return dao.existsByDireccionAllIgnoringCase(direccion);
	}

	//Metodo de validacion de existencia por Email
	public boolean existeEmail(String email) {
		return dao.existsByEmailAllIgnoringCase(email);
	}

	//Metodo de validacion de existencia por Telefono
	public boolean existeTelefono(Long telefono) {
		return dao.existsByTelefono(telefono);
	}


}
