package com.mx.ApiGateway.JWTConfing;

import java.net.Authenticator;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

//Configuracion para seguridad 
@Component 
public class JwtProvider {
	
	//Aqui mandamos a traer a jwt.secret y jwt.expiration desde aplication.properities 
	@Value("${jwt.secret}")
	private String jwtSecret;
	
	@Value("${jwt.expiration}")
	private int jwtExpiration;
	
	//Aqui generamos la clave secreta para firmar el token
	private SecretKey getSigninKey() {
		try {
			byte[] keyBytes = jwtSecret.getBytes("UTF-8"); //Aqui convertimos la clave secreta en un arreglo de bytes
			return Keys.hmacShaKeyFor(keyBytes); //Regresamos la clave secreta generada
		}catch (Exception e) {
			throw new RuntimeException("Error al crear la clave" + e);
		}
	}
	
	//Aqui generamos el token con la informacion del usuario
	public String generateToken(Authentication authentication) {
		UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		
		//Aqui obtenemos la fecha actual y la de expiracion
		Date now = new Date();
		Date expiryDate = new Date(now.getTime()+ jwtExpiration); //La expiracion se mide en milisegundos
		
		//Y por ultimo retornamos el token generado mediante los datos obtenidos y la expiracion
		return Jwts.builder().setSubject(userPrincipal.getUsername()).setIssuedAt(now).setExpiration(expiryDate).signWith(getSigninKey(), SignatureAlgorithm.HS512).compact();
	} 
	
	public boolean validateToken(String token) {
		try {//Validamos que el token sea valido
			Jwts.parserBuilder().setSigningKey(getSigninKey()).build().parseClaimsJws(token);
			return true;
		}catch (JwtException | IllegalArgumentException e) {
			System.out.println("Token invalido" + e.getMessage());
		}
		return false;
	}
	
	//Aqui obtenemos el usaurio del token y verificamos que sea correcto el usuario
	public String getUserFromToken(String token) {
		//Con claims obtenemos el cuerpo del token y regresamos el usuario
		Claims claims = Jwts.parserBuilder().setSigningKey(getSigninKey()).build().parseClaimsJws(token).getBody();
		return claims.getSubject();
	}

}
