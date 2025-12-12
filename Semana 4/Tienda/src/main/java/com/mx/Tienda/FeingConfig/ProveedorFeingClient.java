package com.mx.Tienda.FeingConfig;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mx.Tienda.Dtos.ProveedorDto;

//@Anotacion que indica que esta interfaz es un cliente Feign para comunicarse con otro servicio
//Una de las dos formas en que el cliente puede comunicarse con el servicio 
@FeignClient(name = "Proveedor", url = "http://localhost:8004/proveedores")
public interface ProveedorFeingClient {
	
	//Metodo para listar los proveedores por tienda que solicitamos al servicio de Proveedor
	@GetMapping("/listarPorTienda/{tiendaId}")
	public List<ProveedorDto> listarPorTienda(@PathVariable int tiendaId);

}
