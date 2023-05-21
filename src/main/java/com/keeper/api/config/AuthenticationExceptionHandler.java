package com.keeper.api.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.keeper.api.services.ResponseWriterHelper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationExceptionHandler extends OncePerRequestFilter {
	
	public static final ObjectMapper MAPPER = new ObjectMapper();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		try {
			filterChain.doFilter(request, response);
		}
		catch (AuthenticationException authenticationException) {
			// TODO: handle exception
//			
			Map<String, Object> responseData = new HashMap<>();
			responseData.put("message", authenticationException.getMessage());
			
			ResponseWriterHelper.writeResponse(response,
					HttpStatus.FORBIDDEN.value(), responseData);
		}
		catch (Exception e) {
			// TODO: handle exception
			
			Map<String, Object> responseData = new HashMap<>();
			responseData.put("message", "Something went wrong! Please try again");
			
			ResponseWriterHelper.writeResponse(response,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), responseData);
		}
	}

}
