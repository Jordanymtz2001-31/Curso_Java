package com.mx.transancciones.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mx.transancciones.Dtos.TransaccionRequest;
import com.mx.transancciones.Dtos.TransaccionResponse;
import com.mx.transancciones.Entity.Transaccion;
import com.mx.transancciones.Respository.TransaccionRepository;

@Service
public class TransaccionesService {

	@Autowired//Inyectamos el repositorio
	private TransaccionRepository dao;
	
	//Metodo para guardar en 
	public TransaccionResponse guardar(TransaccionRequest  solici) {
		
		//Creamos la instancia para guardar
		Transaccion t = new Transaccion();
		t.setOperacion(solici.getOperacion());
		t.setImporte(solici.getImporte());
		t.setCliente(solici.getCliente());
		
		//Creamos una referencia aleatoria de 6 digitos
		String referencia = String.format("%06d", (int)(Math.random() * 10000));
		
		t.setReferencia(referencia);
		t.setEstatus("Aprovada");
		
		Transaccion guardar = dao.save(t);
		
		//Pasamoa guardar la espuestas
		TransaccionResponse res = new TransaccionResponse();
		res.setId(guardar.getId());
		res.setEstatus(guardar.getEstatus());
		res.setReferencia(guardar.getReferencia());
		res.setOprecacion(guardar.getOperacion());
		
		return res;
		
	}
	
	//Metodo para listar
	public List<Transaccion> listar(){
		return dao.findAll();
	}

}
