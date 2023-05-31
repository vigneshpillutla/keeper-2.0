package com.keeper.api.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.keeper.api.dto.SignUpDto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@ToString(exclude = {"notes"})
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String firstName;
	private String lastName;
	@Column(name = "username")
	private String username;
	private String password;
	@JsonManagedReference
	@OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
	private List<Note> notes;
	
	public User(SignUpDto signUpDto) {
		this.firstName = signUpDto.getFirstName();
		this.lastName = signUpDto.getLastName();
		this.username = signUpDto.getUsername();
		this.password = signUpDto.getPassword();
		this.notes = new ArrayList<>();
	}

}
