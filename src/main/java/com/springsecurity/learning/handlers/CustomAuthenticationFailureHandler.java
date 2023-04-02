package com.springsecurity.learning.handlers;

import java.io.IOException;
import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		HttpStatus httpStatus = HttpStatus.UNAUTHORIZED; // 401

		Map<String, Object> data = new HashMap<>();
		data.put("timestamp", new Date());
		data.put("code", httpStatus.value());
		data.put("status", httpStatus.name());
		data.put("message", exception.getMessage());

		// setting the response HTTP status code
		response.setStatus(httpStatus.value());

		// serializing the response body in JSON
		response.getOutputStream().println(objectMapper.writeValueAsString(data));

	}
}
