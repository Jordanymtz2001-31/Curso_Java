package com.mx.ApiGateway.SecurityConfig;

import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mx.ApiGateway.JWTConfing.JwtAuthenticationFilter;



//Configuracion de este archivo es para indicar las reglas de seguridad de la aplicacion

@Configuration //Indica que es una clase de configuración
@EnableWebSecurity //Indica junto a @Configuration que es una clase de configuración de seguridad
@EnableMethodSecurity //Indica que se debe activar los metodos de seguridad
public class SecurityConfiguration {
	
	@Lazy
	@Autowired
	private JwtAuthenticationFilter  jwtAuthenticationFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http
		.csrf(csrf -> csrf.disable()) //Por defecot se activa con el WebSecurity/ pero usaaremos confing
		.authorizeHttpRequests(auth -> auth //Nos ayudara a indicar los filtros para las urls
				
				.requestMatchers("/api/auth/**").permitAll() //Rutas publicas que permitAll indica que entra sin autorizacion
				.requestMatchers("/tiendas/**").hasAuthority("ROLE_ADMIN") //Rutas privadas que solo puede entrar el rol de admind
				
				//Rutas por defecto, cuando aplicamos seguridad, pero seguimos modificando la aplicacion
				//debemos asegurar que todo tenemos seguridad y este filtro asegura que todas las rutas que no
				//haya especificado aun, llevaran authentificacion por lo menos 
				.anyRequest().authenticated()) //Esta aqui todas las rutas que maneje apiGateway// todas las aplicaciones deben de entrar por lo menos el login para entrar que es por lo defecto
		
		.sessionManagement(sesion -> sesion //Es un componente que indica como se crea, manejan y destruyen las sessiones http
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //Usamos STATELESS para evitar que una sesion se cierre en automatica, ya que nosotros lo manejaremos
				.addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class); //Indicamos que nuestro filtro se ejecute antes que el filtro de autenticacion por defecto de spring security
		return http.build();
	}
	
	//Este bean nos permite obtener el AuthenticationManager que es el encargado de gestionar la autenticacion
	@Bean 
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	//Este bean nos permite encriptar las contraseñas usando BCryptPasswordEncoder
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
