package com.mx.Cine.Service;

import java.util.List;

import com.mx.Cine.Dominio.Cine;

public interface CineServicio {
	
	//Creamos los metodos CRUD para la entidad Cine
	public void crearCine(Cine cine);
	public List<Cine> leerCines();
	public void actualizarCine(Cine cine);
	public void eliminarCine(Cine idCine);
	public Cine buscarCine(Cine idCine);

}
