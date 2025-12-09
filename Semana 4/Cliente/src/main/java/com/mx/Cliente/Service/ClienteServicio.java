package com.mx.Cliente.Service;

import java.util.List;

import com.mx.Cliente.Entidad.Cliente;

public interface ClienteServicio {
	
	//Metodos que se van a implementar
	public void guardar(Cliente cliente);
	public void editar(Cliente cliente);
	public void eliminar(Integer idCliente);
	public Cliente buscar(Integer idCliente);
	public List<Cliente> listar();
}
