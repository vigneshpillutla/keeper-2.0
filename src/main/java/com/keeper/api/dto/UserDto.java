package com.keeper.api.dto;

import com.keeper.api.entities.Note;
import com.keeper.api.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDto {
	private int id;
	private String firstName,lastName,username;
	private List<Note> notes;

	public UserDto(User user){
		this.id = user.getId();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.username = user.getUsername();
		this.notes = user.getNotes();
	}

	public UserDto(int id, String username) {
		this.id = id;
		this.username = username;
	}

	public UserDto(int id,String username,List<Note> notes){
		this(id,username);
		this.notes = notes;
	}
}
