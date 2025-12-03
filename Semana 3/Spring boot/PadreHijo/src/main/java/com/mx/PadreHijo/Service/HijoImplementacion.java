package com.mx.PadreHijo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mx.PadreHijo.Dao.HijoDao;
import com.mx.PadreHijo.Dominio.Hijo;

@Service //Indica que es un componente de servicio
public class HijoImplementacion  implements HijoService {
	
	@Autowired //Inyección de dependencia
	private HijoDao hijoDao;

	@Override
	public List<Hijo> listarH() {
		// Devuelve la lista de hijos ordenada por idHijo en orden ascendente
		return hijoDao.findAll(Sort.by(Sort.Direction.ASC, "idHijo"));
	}

	@Override
	public void guardarH(Hijo hijo) {
		hijoDao.save(hijo);
		
	}

	@Override
	public void editarH(Hijo hijo) {
		hijoDao.save(hijo);
		
	}

	@Override
	public void eliminarH(Integer idHijo) {
		hijoDao.deleteById(idHijo);
		
	}

	@Override
	public Hijo buscarH(Integer idHijo) {
		return hijoDao.findById(idHijo).orElse(null);
	}
	
	//Método personalizado para buscar hijos por hobbie
	public List<Hijo> buscarPorHobbie(String hobbie) {
		return hijoDao.findByHobbie(hobbie);
	}


}
