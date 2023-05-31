package com.keeper.api.config;

import java.io.IOException;

import com.keeper.api.dto.CredentialsDto;
import com.keeper.api.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class UsernamePasswordAuthFilter extends OncePerRequestFilter {
	
	private static final String END_POINT = "/api/auth/login";
	private static final ObjectMapper MAPPER = new ObjectMapper();
	@Value("${session.cookieName}")
	private String COOKIE_NAME;
	private final AuthenticationManager authenticationManager;
	
	@Autowired
	private AuthenticationService authenticationService;
	
	public UsernamePasswordAuthFilter(AuthenticationManager authenticationManager,AuthenticationService authenticationService) {
		this.authenticationManager = authenticationManager;
		this.authenticationService = authenticationService;
	}

	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response, 
			FilterChain filterChain
			)
			throws ServletException, IOException {
		
		
		if(END_POINT.equals(request.getRequestURI()) 
                && HttpMethod.POST.matches(request.getMethod())) {
			
            CredentialsDto credentialsDto = MAPPER.readValue(request.getInputStream(), CredentialsDto.class);
            
            UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(credentialsDto.getUsername(), 
                    credentialsDto.getPassword());
            
            Authentication auth = this.authenticationManager.authenticate(userToken);
				SecurityContextHolder.getContext().setAuthentication(auth);
	            String username = auth.getPrincipal().toString();
	            response.addCookie(authenticationService.createCookie(username,"/"));
        }
		filterChain.doFilter(request, response);

		
		
	}
	
	

	

}
