package com.shofiqul.config;

import static com.shofiqul.utils.Consts.ROLE_ADMIN;
import static com.shofiqul.utils.Consts.WHITELIST_APIS;
import static com.shofiqul.utils.Consts.ROLE_SUPER_ADMIN;
import static com.shofiqul.utils.Consts.ROLE_USER;

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
	private final CustomAccessDeniedHandler customAccessDeniedHandler;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(req ->
				req
					.requestMatchers(WHITELIST_APIS)
					.permitAll()
					.requestMatchers("/v1/student/**").hasAnyAuthority(ROLE_SUPER_ADMIN, ROLE_ADMIN, ROLE_USER)
					.requestMatchers("/v1/admin/**").hasAnyAuthority(ROLE_SUPER_ADMIN, ROLE_ADMIN)
					.requestMatchers("/v1/super/**").hasAnyAuthority(ROLE_SUPER_ADMIN)
					.anyRequest()
					.authenticated()
				
			)
			.exceptionHandling(e -> e.accessDeniedHandler(customAccessDeniedHandler))
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authenticationProvider(authProvider)
			.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
}
