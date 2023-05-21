package com.keeper.api.dto;

import com.keeper.api.entities.Note;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NoteDto {

    private int id;
    private String title;
    private String content;

    public NoteDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public NoteDto(int id, String title, String content) {
        this(title, content);
        this.id = id;
    }

    public static NoteDto getNote(Note note){
        NoteDto noteDto = new NoteDto();
        noteDto.setId(note.getId());
        noteDto.setTitle(note.getTitle());
        noteDto.setContent(note.getContent());

        return noteDto;
    }
}
