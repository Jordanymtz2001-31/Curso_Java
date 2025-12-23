package com.mx.Tienda.FeingConfig;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mx.Tienda.Dtos.ClienteDto;

//@Anotacion que indica que esta interfaz es un cliente Feign para comunicarse con otro servicio
//Una de las dos formas en que el cliente puede comunicarse con el servicio 
@FeignClient(name = "Cliente", url = "http://localhost:8006/clientes")
public interface ClientesFeignClient {
	
	//Metodo de listar por cliente del otro micro servicio
	@GetMapping("/listarXTienda/{tiendaId}")
	public List<ClienteDto> buscarXTienda(@PathVariable int tiendaId);

}
	