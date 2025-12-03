package com.mx.PadreHijo.Service;

import java.util.List;

import com.mx.PadreHijo.Dominio.Padre;

public interface PadreService {
	
	//Definimos los métodos que tendrá el servicio de Padre
	public List<Padre> listarP();
	public void guardarP(Padre padre);
	public void editarP(Padre padre);
	public void eliminarP(Integer idPadre);
	public Padre buscarP(Integer idPadre);

}
