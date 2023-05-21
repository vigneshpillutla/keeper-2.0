package com.keeper.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test/secure")
public class TestSecurityController {
    @GetMapping
    public ResponseEntity<String> securePage(){
        return ResponseEntity.ok("User is Logged In!");
    }
}
