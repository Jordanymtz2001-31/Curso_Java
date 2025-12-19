package com.mx.ApiGateway.JWTConfing;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mx.ApiGateway.UserDetailsConfing.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component //indicamos que es un componente  
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	//Inyectamos 
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	//Metodo para obtener el token de la peticion
	private String getTokenFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization"); //Obtenemos el token del encabezado de la peticion
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) { //Validamos que el token no sea nulo y que empiece con Bearer
			return bearerToken.substring(7); //Regresamos el token sin la palabra Bearer
		}
		return null;
	}

	//Aqui validamos el token en cada peticion 
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = getTokenFromRequest(request); //Obtenemos el token de la peticion
		
		if(token != null && jwtProvider.validateToken(token)) { //Validamos que el token no sea nulo y que sea valido
			String username = jwtProvider.getUserFromToken(token); //Obtenemos el nombre de usuario del token
			
			UserDetails userDetails = customUserDetailsService.loadUserByUsername(username); //Cargamos los detalles del usuario
			
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request,response);

	}
	
	
	

}
