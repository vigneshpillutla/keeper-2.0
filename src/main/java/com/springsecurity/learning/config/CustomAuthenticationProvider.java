package com.springsecurity.learning.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import com.springsecurity.learning.dto.CredentialsDto;
import com.springsecurity.learning.dto.UserDto;
import com.springsecurity.learning.services.AuthenticationService;

import lombok.AllArgsConstructor;


@Component
@AllArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private final AuthenticationService authenticationService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		UserDto userDto = null;
		if(authentication instanceof UsernamePasswordAuthenticationToken) {
			userDto = authenticationService.authenticate(
					new CredentialsDto((String)authentication.getPrincipal(),
							(String)authentication.getCredentials()));
		}
		else if(authentication instanceof PreAuthenticatedAuthenticationToken) {
			userDto = authenticationService.findByToken((String)authentication.getPrincipal());
			System.out.println(userDto);
		}
		
		if(userDto==null)return null;
		
		return new UsernamePasswordAuthenticationToken(userDto.getUsername(), 
				null,
				Collections.<SimpleGrantedAuthority>emptyList()
		); 
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
