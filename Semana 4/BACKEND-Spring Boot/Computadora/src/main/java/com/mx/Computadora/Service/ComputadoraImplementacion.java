package com.mx.Computadora.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mx.Computadora.Dao.ComputadoraDao;
// Indica que esta clase es un servicio del proyecto
import com.mx.Computadora.Dominio.Computadora;

@Service // Indica que esta clase es un servicio del proyecto
public class ComputadoraImplementacion implements ComputadoraService {
	
	@Autowired // Inyección de dependencia del repositorio
	private ComputadoraDao compuDao;

	@Override
	public void gurdar(Computadora computadora) {
		compuDao.save(computadora);
		
	}

	@Override
	public void editar(Computadora computadora) {
		compuDao.save(computadora);
		
	}

	@Override
	public void eliminar(Integer idCompu) {
		compuDao.deleteById(idCompu);
		
	}

	@Override
	public Computadora buscar(Integer idCompu) {
		return compuDao.findById(idCompu).orElse(null);	
	}

	@Override
	public List<Computadora> listar() {
		return compuDao.findAll(Sort.by(Sort.Direction.ASC, "idCompu"));
	}
	
	// Metodo para verificar si existe una computadora por marca y modelo
	public boolean existeCompu(String marca, String modelo) {
		return compuDao.existsByMarcaAndModeloAllIgnoringCase(marca, modelo);
	}
	
	// Metodo para aplicar descuento a todas las computadoras
	public void aplicarDescuento(Integer descuento) {
		if(descuento > 0 && descuento < 100) {
			compuDao.aplicarDescuento(descuento);
		}else {
			throw new IllegalArgumentException("El descuento debe estar entre 0 y 100");
		}
	}

}
