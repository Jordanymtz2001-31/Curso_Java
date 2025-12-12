package com.mx.Tienda.FeingConfig;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mx.Tienda.Dtos.DepartamentoDto;

//Es una anotacion que indica que esta interfaz es un cliente Feign para comunicarse con otro servicio
//hace peticiones HTTP a otro servicio, y para que aga la solicitud necesita del name y la url del servicio
@FeignClient(name = "Departamento", url = "http://localhost:8003/departamento")
public interface DepartamentoFeingClient {
	
	//Metodo para listar departamentos por tienda que solicitamos al servicio de Departamento
	@GetMapping("/listarPorTienda/{tiendaId}")
	public List<DepartamentoDto> listarPorTienda(@PathVariable int tiendaId);

}
 