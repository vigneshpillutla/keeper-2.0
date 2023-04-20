package com.springsecurity.learning.config;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.springsecurity.learning.services.AuthenticationService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CookieAuthFilter extends OncePerRequestFilter {
	
	@Value("${session.cookieName}")
	private String COOKIE_NAME;

	private AuthenticationManager authenticationManager;

	
	
	public CookieAuthFilter(AuthenticationManager authenticationManager) {
		super();
		this.authenticationManager = authenticationManager;
	}



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		Optional<Cookie> cookieAuth = Stream.of(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
				.filter(cookie -> COOKIE_NAME.equals(cookie.getName()))
				.findFirst();
		
		
		cookieAuth.ifPresent(cookie -> {
			Authentication authentication = authenticationManager.authenticate(
					new PreAuthenticatedAuthenticationToken(cookie.getValue(), null)
					);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		});
		
		
		filterChain.doFilter(request, response);
	}
	

}
