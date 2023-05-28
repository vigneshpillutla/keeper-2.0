package com.keeper.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestSecurityController {
    @GetMapping(path = "/secure")
    public ResponseEntity<String> securePage(){
        return ResponseEntity.ok("User is Logged In!");
    }

    @GetMapping(path = "/public")
    public ResponseEntity<String> publicTestPage(){
        return ResponseEntity.ok("API is up and running!");
    }
}
