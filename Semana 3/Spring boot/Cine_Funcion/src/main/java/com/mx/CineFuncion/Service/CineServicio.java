package com.mx.CineFuncion.Service;

import java.util.List;

import com.mx.CineFuncion.Dominio.Cine;


public interface CineServicio {
	
	//Definir los métodos que se implementarán en la clase de servicio
	public List<Cine> listarC();
	public void guardarC(Cine cine);
	public void editarC(Cine cine);
	public void eliminarC(Integer idCine);
	public Cine buscarC(Integer idCine);

}
