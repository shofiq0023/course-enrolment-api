package com.shofiqul.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final AuthenticationProvider authProvider;
	private final SecurityFilter securityFilter;
	
	private final String SUPER_ADMIN = "ROLE_SUPER_ADMIN";
	private final String ADMIN = "ROLE_ADMIN";
	private final String STUDENT = "ROLE_STUDENT";
	
	private final String[] apiWhiteList = {"/v1/user/authenticate", "/v1/user/register"};
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(req ->
				req
					.requestMatchers(apiWhiteList)
					.permitAll()
					.requestMatchers("/v1/student/**").hasAnyAuthority(SUPER_ADMIN, ADMIN, STUDENT)
					.requestMatchers("/v1/admin/**").hasAnyAuthority(SUPER_ADMIN, ADMIN)
					.requestMatchers("/v1/super/**").hasAnyAuthority(SUPER_ADMIN)
					.anyRequest()
					.authenticated()
				
			)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authenticationProvider(authProvider)
			.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
}
