package com.keeper.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import jakarta.servlet.http.Cookie;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {
	
	private static final String LOGOUT_ENDPOINT = "/api/auth/logout";
	
	private String[] PUBLIC_URLS = {"/api/auth/login","/api/auth/signup",LOGOUT_ENDPOINT}; 
	
	@Value("${session.cookieName}")
	private String COOKIE_NAME;
			
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(CustomAuthenticationProvider customAuthenticationProvider) {
		return new ProviderManager(customAuthenticationProvider);
	}
	
	@Bean
	public SecurityFilterChain filterChain(
			HttpSecurity httpSecurity, 
			UsernamePasswordAuthFilter usernamePasswordAuthFilter,
			CookieAuthFilter cookieAuthFilter,
			AuthenticationExceptionHandler authenticationExceptionHandler
			) throws Exception {
		
		/*Configure the logout handler*/
		LogoutSuccessHandler logoutSuccessHandler = (request, response, auth) -> {
			Cookie sessionCookie = new Cookie(COOKIE_NAME, null);
			sessionCookie.setMaxAge(0);
			response.addCookie(sessionCookie);
        };
		
		httpSecurity
			.logout()
			.logoutUrl(LOGOUT_ENDPOINT)
			.logoutSuccessHandler(logoutSuccessHandler)
			.and()
			.addFilterBefore(usernamePasswordAuthFilter, ExceptionTranslationFilter.class)
			.addFilterAfter(cookieAuthFilter,UsernamePasswordAuthFilter.class)
			.addFilterBefore(authenticationExceptionHandler, UsernamePasswordAuthFilter.class)
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeHttpRequests()
			.requestMatchers(PUBLIC_URLS)
			.permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.httpBasic().disable()
			.formLogin().disable()
			.csrf().disable();
		
		return httpSecurity.build();
	}
}
