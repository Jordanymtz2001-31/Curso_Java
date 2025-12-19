package com.mx.Cliente.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mx.Cliente.Entidad.Cliente;
import com.mx.Cliente.Repositorio.ClienteRepository;

@Service //Indica que esta clase es un servicio de Spring
public class ClienteImplementacion implements ClienteServicio {
	
	//Inyectar el repositorio
	@Autowired
	private ClienteRepository dao;

	@Override
	public void guardar(Cliente cliente) {
		dao.save(cliente);
		
	}

	@Override
	public void editar(Cliente cliente) {
		dao.save(cliente);
		
	}

	@Override
	public void eliminar(Integer id) {
		dao.deleteById(id);
		
	}

	@Override
	public Cliente buscar(Integer id) {
		return dao.findById(id).orElse(null);
	}

	@Override
	public List<Cliente> listar() {
		return dao.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}
	
	//Metodos de si existe cliente
	public boolean existeCliente(String nombre, String apellidoP, String apellidoM) {
		return dao.existsByNombreAndApellidoPAndApellidoMAllIgnoringCase(nombre, apellidoP, apellidoM);
	}
	
	//Metodo de verificar si existe telefono
	public boolean existeTelefono(Long telefono) {
		return dao.existsByTelefono(telefono);
	}
	
	//Metodo de listar clientes por tienda
	public List<Cliente> listarPorTienda(Integer tiendaId) {
		return dao.findByTiendaId(tiendaId);
	}
	
	

}
