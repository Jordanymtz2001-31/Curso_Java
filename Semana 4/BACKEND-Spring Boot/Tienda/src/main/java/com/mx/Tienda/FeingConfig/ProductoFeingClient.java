package com.mx.Tienda.FeingConfig;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mx.Tienda.Dtos.ProductoDto;

//@Anotacion que indica que esta interfaz es un cliente Feign para comunicarse con otro servicio
//Una de las dos formas en que el cliente puede comunicarse con el servicio 
@FeignClient(name = "Producto", url = "http://localhost:8002/productos")
public interface ProductoFeingClient {
	
	//Colocamos los metodos que necesitamos para comunicarnos con el servicio de Producto
	@GetMapping("/tienda/{tiendaId}")
	public List<ProductoDto> ListarProductosPorTienda(@PathVariable int tiendaId);

}
