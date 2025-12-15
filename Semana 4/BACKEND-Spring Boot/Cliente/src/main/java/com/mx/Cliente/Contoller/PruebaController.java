package com.mx.Cliente.Contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class PruebaController {
	
	@RequestMapping("/")
	public @ResponseBody String saludo() {
		return "Hola Mundo";
	}
}
