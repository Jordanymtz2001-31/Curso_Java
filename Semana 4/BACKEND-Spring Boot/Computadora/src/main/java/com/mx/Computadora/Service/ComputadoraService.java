package com.mx.Computadora.Service;

import java.util.List;

import com.mx.Computadora.Dominio.Computadora;

public interface ComputadoraService {
	
	// Metodos CRUD
	public void gurdar(Computadora computadora);
	public void editar(Computadora computadora);
	public void eliminar(Integer idCompu);
	public Computadora buscar(Integer idCompu);
	public List<Computadora> listar();
	

}
