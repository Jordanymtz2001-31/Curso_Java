package com.mx.ApiGateway.UserDetailsConfing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//Este servicio se encarga de cargar los detalles del usuario para la autenticacion

@Service //Indica que es un servicio de Spring
public class CustomUserDetailsService implements UserDetailsService {
	
	@Lazy
	@Autowired
	private PasswordEncoder pas;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if("admin".equals(username)) {
			UserDetails user = User.builder()
					.username("admin")
					.password(pas.encode("admin123"))
					.roles("ADMIN")
					.build();
			return user;
		}
		if("user".equals(username)) {
			UserDetails user = User.builder()
					.username("user")
					.password(pas.encode("user123"))
					.roles("USER")
					.build();
			return user;
		}
		throw new UsernameNotFoundException("Usuario no encontrado: " + username);
	}

}
