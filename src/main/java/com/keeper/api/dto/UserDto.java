package com.keeper.api.dto;

import com.keeper.api.entities.Note;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDto {
	private int id;
	private String username;
	private List<Note> notes;

	public UserDto(int id, String username) {
		this.id = id;
		this.username = username;
	}

	public UserDto(int id,String username,List<Note> notes){
		this(id,username);
		this.notes = notes;
	}
}
