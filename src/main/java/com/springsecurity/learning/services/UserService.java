package com.springsecurity.learning.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springsecurity.learning.dao.UserRepository;
import com.springsecurity.learning.dto.SignUpDto;
import com.springsecurity.learning.dto.UserDto;
import com.springsecurity.learning.entities.User;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
	private UserRepository userRepository;
	private BCryptPasswordEncoder passwordEncoder;
	
	public User getUser(String username) {
		User user = userRepository.findByUsername(username);
		return user;
	}
	
	public User saveUser(SignUpDto signUpDto) {
		signUpDto.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
		User newUser = new User(signUpDto);
		
		return userRepository.save(newUser);
	}
	
}
