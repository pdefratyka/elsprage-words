package com.elsprage.words.web.controller;

import com.elsprage.words.model.dto.LanguageDTO;
import com.elsprage.words.model.dto.WordDTO;
import com.elsprage.words.service.LanguageService;
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
    private final LanguageService languageService;

    @GetMapping("/languages")
    public ResponseEntity<List<LanguageDTO>> getLanguages() {
        return ResponseEntity.ok(languageService.getLanguages());
    }

    @PostMapping
    public ResponseEntity<WordDTO> saveWord(@RequestBody WordDTO word) {
        log.info("Save word: " + word);
        final WordDTO savedWord = wordsService.saveWord(word);
        return ResponseEntity.ok(savedWord);
    }

    @GetMapping
    public List<WordDTO> getAllWords() {
        return wordsService.getAllWords();
    }
}