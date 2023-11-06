package com.shofiqul.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration.time}")
	private long expiration;
	
	public String extractUsername(String token) {
		return extractClaims(token, Claims::getSubject);
	}
	
	private Date extractExpirationDate(String token) {
		return extractClaims(token, Claims::getExpiration);
	}
	
	public String generateToken(String subject) {
		return buildToken(new HashMap<String, Object>(), subject);
	}
	
	public String generateToken(HashMap<String, Object> claims, String subject) {
		return buildToken(claims, subject);
	}
	
	public Boolean isTokenExpired(String token) {
		return extractExpirationDate(token).before(new Date(System.currentTimeMillis()));
	}
	
	public Boolean isTokenValid(String token, UserDetails userDetails) {
		String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	private String buildToken(HashMap<String, Object> claims, String subject) {
		return Jwts
				.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getSignKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
		Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	private Key getSignKey() {
		byte[] secretByte = Decoders.BASE64.decode(secret);
		return Keys.hmacShaKeyFor(secretByte);
	}
}
