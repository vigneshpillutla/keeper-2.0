package com.springsecurity.learning.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springsecurity.learning.dao.UserRepository;
import com.springsecurity.learning.dto.SignUpDto;
import com.springsecurity.learning.dto.UserDto;
import com.springsecurity.learning.entities.User;
import com.springsecurity.learning.services.AuthenticationService;
import com.springsecurity.learning.services.UserService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private UserService userService;
	
	

	public AuthController(UserService userService) {
		super();
		this.userService = userService;
	}

	@PostMapping("/login")
	public ResponseEntity<UserDto> login(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		User user = userService.getUser((String)authentication.getPrincipal());
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setUsername(user.getUsername());
		
		
		return ResponseEntity.ok(userDto);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto){
		User newUser = userService.saveUser(signUpDto);
		return ResponseEntity.ok(new UserDto(newUser.getId(), newUser.getUsername()));
	}
	
	@GetMapping("/test/secure")
	public ResponseEntity<String> securePage(){
		return ResponseEntity.ok("User is Logged In!");
	}
}