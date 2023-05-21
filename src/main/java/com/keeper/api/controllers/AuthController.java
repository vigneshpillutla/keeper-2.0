package com.keeper.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keeper.api.dto.SignUpDto;
import com.keeper.api.dto.UserDto;
import com.keeper.api.entities.User;
import com.keeper.api.response.StandardResponse;
import com.keeper.api.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private UserService userService;
	
	

	public AuthController(UserService userService) {
		super();
		this.userService = userService;
	}

	@PostMapping("/login")
	public ResponseEntity<StandardResponse> login(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		User user = userService.getUser((String)authentication.getPrincipal());
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setUsername(user.getUsername());
		userDto.setNotes(user.getNotes());

		StandardResponse response = new StandardResponse(true, "Successfully Logged In!!", userDto);
		
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<StandardResponse> signUp(@RequestBody SignUpDto signUpDto){
		User newUser = userService.saveUser(signUpDto);
		UserDto userDto = new UserDto(newUser.getId(), newUser.getUsername());
		
		StandardResponse response = new StandardResponse(true,"Successfully Signed up!",userDto);
		return ResponseEntity.ok(response);
	}
	

}
