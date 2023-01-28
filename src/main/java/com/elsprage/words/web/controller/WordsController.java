package com.elsprage.words.web.controller;

import com.elsprage.words.model.dto.WordDTO;
import com.elsprage.words.model.request.WordRequest;
import com.elsprage.words.model.response.WordResponse;
import com.elsprage.words.service.WordValidationService;
import com.elsprage.words.service.WordsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/words")
@AllArgsConstructor
@Slf4j
public class WordsController {

    private final WordsService wordsService;
    private final WordValidationService wordValidationService;

    @PostMapping
    public ResponseEntity<WordResponse> saveWord(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody WordRequest wordRequest) {
        log.info("Save word: {}", wordRequest);
        wordValidationService.validateWordRequest(wordRequest);
        final WordDTO savedWord = wordsService.saveWord(wordRequest, token);
        final WordResponse wordResponse = new WordResponse(savedWord);
        return ResponseEntity.ok(wordResponse);
    }

    @GetMapping
    public List<WordDTO> getAllWords() {
        return wordsService.getAllWords();
    }
}