package com.springsecurity.learning.entities;

import com.springsecurity.learning.dto.SignUpDto;
import com.springsecurity.learning.dto.UserDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String username;
	private String password;
	
	public User(SignUpDto signUpDto) {
		this.username = signUpDto.getUsername();
		this.password = signUpDto.getPassword();
	}
}
