package com.elsprage.words.web.controller;

import com.elsprage.words.model.dto.WordDTO;
import com.elsprage.words.service.WordsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/words")
@AllArgsConstructor
@Slf4j
public class WordsController {

    private final WordsService wordsService;

    @PostMapping
    public ResponseEntity<WordDTO> saveWord(@RequestBody WordDTO word) {
        log.info("Save word: " + word);
        final WordDTO savedWord = wordsService.saveWord(word);
        return ResponseEntity.ok(savedWord);
    }

    @GetMapping
    public List<String> getAllWords() {
        return List.of("Cat", "Dog", "Horse");
    }
}