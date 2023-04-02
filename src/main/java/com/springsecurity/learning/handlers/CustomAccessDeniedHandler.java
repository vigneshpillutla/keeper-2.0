package com.springsecurity.learning.handlers;

import java.io.IOException;
import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception)
			throws IOException, ServletException {
		HttpStatus httpStatus = HttpStatus.FORBIDDEN; // 403

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
