package com.keeper.api.services;

import com.keeper.api.dao.NoteRepository;
import com.keeper.api.dto.NoteDto;
import com.keeper.api.entities.Note;
import com.keeper.api.entities.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NoteService {
    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public NoteDto saveNote(String title, String content, User user){
        Note newNote = new Note(title,content);
        newNote.setUser(user);
        Note savedNote = noteRepository.save(newNote);
        newNote.setId(savedNote.getId());

        return NoteDto.getNote(newNote);
    }

    public List<NoteDto> getAllNotes(String username){
        List<Note> notes = noteRepository.findByUser_Username(username);

        return notes.stream().map(NoteDto::getNote).toList();
    }

    @Transactional
    public NoteDto updateNote(NoteDto noteDto){
        noteRepository.setNoteById(noteDto.getTitle(), noteDto.getContent(), noteDto.getId());

//        return NoteDto.getNote(updatedNote);
        return noteDto;
    }

    public void deleteNote(int id){
        noteRepository.deleteById(id);
    }
}
