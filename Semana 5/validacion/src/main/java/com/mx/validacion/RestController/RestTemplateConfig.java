package com.mx.validacion.RestController;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration // Indica que esta clase contiene configuraciones de Spring y puede definir beans
public class RestTemplateConfig {
	
	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
