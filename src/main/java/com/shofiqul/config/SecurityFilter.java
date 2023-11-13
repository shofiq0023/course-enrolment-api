package com.shofiqul.config;

import static com.shofiqul.utils.Consts.WHITELIST_APIS;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shofiqul.exceptions.TokenMissingException;
import com.shofiqul.services.ResponseService;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
	private final JwtService jwtService;
	private final CustomUserDetailsService userDetailsService;
	private final ResponseService resService;
	private final String[] whitelistApi = WHITELIST_APIS;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;
		
		try {
			List<String> ignoreApi = Arrays.asList(whitelistApi);
			if (ignoreApi.contains(request.getServletPath())) {
				filterChain.doFilter(request, response);
				return;
			}
			
			if (authHeader == null || !authHeader.startsWith("Bearer ")) {
				throw new TokenMissingException("Authorization header missing");
			}

			token = authHeader.substring(7);
			username = jwtService.extractUsername(token);

			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);

				if (jwtService.isTokenValid(token, userDetails)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,
							null, userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}

			filterChain.doFilter(request, response);
		} catch (Exception e) {
			HttpStatus status = null;
			ResponseEntity<?> responseEntity = null;
			
			if (e instanceof TokenMissingException) {
				status = HttpStatus.BAD_REQUEST;
				responseEntity = resService.createResponse(e.getMessage(), status);
			} else if (e instanceof ExpiredJwtException) {
				status = HttpStatus.UNAUTHORIZED;
				responseEntity = resService.createResponse("Token expired!", status);
			} else if (e instanceof UsernameNotFoundException) {
				status = HttpStatus.NOT_FOUND;
				responseEntity = resService.createResponse("User not found", status);
			} else {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
				responseEntity = resService.createResponse("Something went wrong!", status);
			}
			
			ObjectMapper objectMapper = new ObjectMapper();
	        String jsonResponse = objectMapper.writeValueAsString(responseEntity.getBody());
	        
			response.setContentType("application/json");
	        response.setStatus(status.value());
	        response.getWriter().write(jsonResponse);
		}

	}

}
