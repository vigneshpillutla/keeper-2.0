package com.keeper.api.controllers;

import com.keeper.api.services.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.keeper.api.dto.SignUpDto;
import com.keeper.api.dto.UserDto;
import com.keeper.api.entities.User;
import com.keeper.api.response.StandardResponse;
import com.keeper.api.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final UserService userService;
	private final AuthenticationService authenticationService;
	
	

	public AuthController(UserService userService,AuthenticationService authenticationService) {
		super();
		this.userService = userService;
		this.authenticationService = authenticationService;
	}

	@GetMapping("/user")
	public ResponseEntity<StandardResponse> getUser(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getPrincipal().toString();


		StandardResponse response = new StandardResponse(true, "User is Logged In!!", userService.getUserDto(username));

		return ResponseEntity.ok(response);
	}

	@PostMapping("/login")
	public ResponseEntity<StandardResponse> login(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getPrincipal().toString();

		StandardResponse response = new StandardResponse(true, "Successfully Logged In!!", userService.getUserDto(username));
		
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<StandardResponse> signUp(@RequestBody SignUpDto signUpDto, HttpServletResponse httpServletResponse){
		User newUser = userService.saveUser(signUpDto);
		UserDto userDto = new UserDto(newUser);

		httpServletResponse.addCookie(
				authenticationService.createCookie(userDto.getUsername(),"/")
		);

		StandardResponse response = new StandardResponse(true,"Successfully Signed up!",userDto);
		return ResponseEntity.ok(response);
	}
	

}
