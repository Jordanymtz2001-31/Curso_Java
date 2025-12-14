package com.mx.Tienda.FeingConfig;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mx.Tienda.Dtos.EmpleadoDto;


//@Anotacion que indica que esta interfaz es un cliente Feign para comunicarse con otro servicio
//Una de las dos formas en que el cliente puede comunicarse con el servicio 
@FeignClient(name = "Empleado", url = "http://localhost:8001/empleados")
public interface EmpleadoFeingClient {
	
	//Colocamos los metodos que necesitamos para comunicarnos con el servicio de Empleado
	@GetMapping("/listTienda/{tiendaId}")
	public List<EmpleadoDto> ListarPorTiendaId(@PathVariable int tiendaId);

}
