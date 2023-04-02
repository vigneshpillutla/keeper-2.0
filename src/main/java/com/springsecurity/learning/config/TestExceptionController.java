package com.springsecurity.learning.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TestExceptionController {
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<String> handler(BadCredentialsException e){
		return new ResponseEntity<String>("Error", HttpStatus.BAD_REQUEST);
	}

}
