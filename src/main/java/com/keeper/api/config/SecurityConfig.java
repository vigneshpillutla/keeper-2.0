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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {
	
	private static final String LOGOUT_ENDPOINT = "/api/auth/logout";
	
	private final String[] PUBLIC_URLS = {"/api/auth/login","/api/auth/signup",LOGOUT_ENDPOINT,"/api/test/public"};
	private final List<String> ALLOWED_DOMAINS = List.of("http://localhost:3000","https://keep-er.netlify.app");
	
	@Value("${session.cookieName}")
	private String COOKIE_NAME;
			
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CorsFilter corsFilter(){
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();

		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(ALLOWED_DOMAINS);
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addAllowedMethod("*");
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
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
				.cors().and()
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
