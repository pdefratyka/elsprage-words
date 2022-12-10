package com.elsprage.words.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/words")
public class WordsController {

    @GetMapping
    public List<String> getAllWords() {
        return List.of("Cat", "Dog", "Horse");
    }
}