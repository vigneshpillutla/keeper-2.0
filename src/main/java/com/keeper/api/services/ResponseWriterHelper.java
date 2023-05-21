package com.keeper.api.services;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;

public class ResponseWriterHelper {
	
	private static ObjectMapper MAPPER = new ObjectMapper();

	public static void writeResponse(
			HttpServletResponse response,
			int status,
			String contentType,
			Map<String, Object> body) throws StreamWriteException, DatabindException, IOException {
		response.setStatus(status);
		response.setContentType(contentType);
		
		MAPPER.writeValue(response.getWriter(), body);
	}
	
	public static void writeResponse(
			HttpServletResponse response,
			int status,
			Map<String, Object> body) throws StreamWriteException, DatabindException, IOException {
		writeResponse(response, status, MediaType.APPLICATION_JSON_VALUE,body);
	}
}
