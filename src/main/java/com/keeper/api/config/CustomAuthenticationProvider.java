package com.keeper.api.config;

import java.util.Collections;

import com.keeper.api.services.AuthenticationService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import com.keeper.api.dto.CredentialsDto;
import com.keeper.api.dto.UserDto;

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
