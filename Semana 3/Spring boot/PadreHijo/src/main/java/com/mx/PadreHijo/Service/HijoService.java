package com.mx.PadreHijo.Service;

import java.util.List;

import com.mx.PadreHijo.Dominio.Hijo;

public interface HijoService {

	//Definimos los métodos que tendrá el servicio de Hijo
		public List<Hijo> listarH();
		public void guardarH(Hijo padre);
		public void editarH(Hijo padre);
		public void eliminarH(Integer idPadre);
		public Hijo buscarH(Integer idPadre);
	
}
