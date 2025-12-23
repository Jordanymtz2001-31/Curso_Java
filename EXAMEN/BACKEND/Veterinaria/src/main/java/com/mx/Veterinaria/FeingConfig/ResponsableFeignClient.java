package com.mx.Veterinaria.FeingConfig;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mx.Veterinaria.Dtos.ResponsableDto;

//Es una anotacion que indica que esta interfaz es un cliente Feign para comunicarse con otro servicio
//hace peticiones HTTP a otro servicio, y para que aga la solicitud necesita del name y la url del servicio
@FeignClient(name = "Responsables", url = "http://localhost:8003/responsable")
public interface ResponsableFeignClient {
	
	//Metodo para listar Mascotas por veterinaria que solicitamos al servicio de Mascotas
	@GetMapping("/listaResponsables/{veterinariaId}")
	public List<ResponsableDto> listarRPorVeterinaria(@PathVariable int veterinariaId);

}
