package com.mx.Cajero.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mx.Cajero.Dao.CajeroDao;
import com.mx.Cajero.Dominio.Cajero;
import com.mx.Cajero.Dto.DenominacionesEntregadas;
import com.mx.Cajero.Dto.Respuesta_Retiro;

@Service //Indicamos que esta clase es un servicio de Spring
public class CajeroServices {
	
	@Autowired //Inyeccion de dependencia del Dao
	private CajeroDao Dao;
	
	//Metodo de respuesta para el retiro de dinero
	public Respuesta_Retiro procesoRetiro(Float monto) {
		//Validamos si es mayor al limite del cajero manda una excepcion
		if (monto.compareTo(Float.valueOf((float) 12550.50)) > 0)
			throw new IllegalArgumentException("Monto excede el limite disponible en el cajero.");
		
		//Creamos una lista para almacenar el inventario de billetes
		List<Cajero> invetario = Dao.findAllByOrderByDenominacionDesc();
		
		//Creamos una lista para almacenar las denominaciones entregadas
		List<DenominacionesEntregadas> entregadas = new ArrayList<>();
		
		//Guardamos el saldo inicial del cajero
		Float saldoInicial = obtenerSaldoCajero();
		
		//Monto restante por entregar
		Float montoRestante = monto;
		
		for(Cajero deno : invetario) {
			if(montoRestante > 0.01f) {
				Float valorDeno = deno.getDenominacion();
				Integer cantidadDeno = deno.getCantidad();
				
				//Calculamos cuántos billetes de esta denominación se pueden entregar
				Integer billetesNecesarios = Math.toIntExact(Math.round(montoRestante / valorDeno));
				Integer billetesAEntregar = Math.min(billetesNecesarios, cantidadDeno);
				
				if(billetesAEntregar > 0) {
					
					//Calculamos el monto restante después de entregar los billetes
					montoRestante -=(billetesAEntregar * valorDeno);
					montoRestante = Math.round(montoRestante * 100.0f) / 100.0f;
					//Actualizamos la cantidad de billetes en el inventario
					deno.setCantidad(cantidadDeno - billetesAEntregar);
					//Guardamos los cambios en la base de datos
					Dao.save(deno);
					//Agregamos la denominación entregada a la lista
					entregadas.add(new DenominacionesEntregadas(valorDeno, billetesAEntregar));
				}
				
			}
		}
		//Retornamos la respuesta del retiro
		return new Respuesta_Retiro(
				montoRestante == 0,
				Float.valueOf(monto - montoRestante),
				entregadas,
				saldoInicial - Float.valueOf(monto - montoRestante)
				);
	}
	
	//Metodo para reinicializar el cajero
	public void reiniciarCajero() {
		//Eliminamos todos los registros del cajero
		Dao.deleteAll();
		
		//Agregamos las denominaciones iniciales
		Dao.save(new Cajero(null, "Billete", 2, 1000.0f));
		Dao.save(new Cajero(null, "Billete", 5, 500.0f));
		Dao.save(new Cajero(null, "Billete", 10, 200.0f));
		Dao.save(new Cajero(null, "Billete", 20, 100.0f));
		Dao.save(new Cajero(null, "Billete", 30, 50.0f));
		Dao.save(new Cajero(null, "Billete", 40, 20.0f));
		Dao.save(new Cajero(null, "Moneda", 50, 10.0f));
		Dao.save(new Cajero(null, "Moneda", 100, 5.0f));
		Dao.save(new Cajero(null, "Moneda", 200, 2.0f));
		Dao.save(new Cajero(null, "Moneda", 300, 1.0f));
		Dao.save(new Cajero(null, "Moneda", 400, 0.5f));
		
	}
	
	//Metodo para obtener el saldo total del cajero
	public Float obtenerSaldoCajero() {
		//Obtenemos el inventario completo
		List<Cajero> inventario = Dao.findAll();
		Float saldoTotal = 0.0f;
		
		//Calculamos el saldo total
		for(Cajero deno : inventario) {
			saldoTotal += deno.getDenominacion() * deno.getCantidad();
		}
		
		return saldoTotal;
	}
	
	//Metodo para obtener el inventario del cajero
	public List<Cajero> obtenerInventarioCajero() {
		return Dao.findAllByOrderByDenominacionDesc();
	}
	
	
		
	

}
