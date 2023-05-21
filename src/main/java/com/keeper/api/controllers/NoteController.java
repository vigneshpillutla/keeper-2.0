package com.keeper.api.controllers;

import com.keeper.api.dto.NoteDto;
import com.keeper.api.entities.User;
import com.keeper.api.response.StandardResponse;
import com.keeper.api.services.NoteService;
import com.keeper.api.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/note")
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<StandardResponse> saveNote(@RequestBody Map<String,Object> body){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUser(authentication.getPrincipal().toString());
        String title = body.get("title").toString(),content = body.get("content").toString();
        NoteDto savedNote = noteService.saveNote(title,content,user);

        StandardResponse response = new StandardResponse(true,"Note Saved!",savedNote);

        return ResponseEntity.ok(response);
    }
}
