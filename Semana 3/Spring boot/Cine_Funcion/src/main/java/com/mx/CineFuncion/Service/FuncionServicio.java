package com.mx.CineFuncion.Service;

import java.util.List;

import com.mx.CineFuncion.Dominio.Funcion;

public interface FuncionServicio {
	
	//Definir los métodos que se implementarán en la clase de servicio
	public List<Funcion> listarF();
	public void guardarF(Funcion funcion);
	public void editarF(Funcion funcion);
	public void eliminarF(Integer idFuncion);
	public Funcion buscarF(Integer idFuncion);

}
