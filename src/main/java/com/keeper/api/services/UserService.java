package com.keeper.api.services;

import com.keeper.api.dao.UserRepository;
import com.keeper.api.dto.UserDto;
import com.keeper.api.entities.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.keeper.api.dto.SignUpDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
	private UserRepository userRepository;
	private BCryptPasswordEncoder passwordEncoder;
	
	public User getUser(String username) {
		return userRepository.findByUsername(username);
	}

	public UserDto getUserDto(String username){
		User user = getUser(username);

		return new UserDto(user);
	}
	
	public User saveUser(SignUpDto signUpDto) {
		signUpDto.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
		User newUser = new User(signUpDto);
		
		return userRepository.save(newUser);
	}
	
}
