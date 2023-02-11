package com.elsprage.words.web.controller;

import com.elsprage.words.model.dto.WordDTO;
import com.elsprage.words.model.request.WordRequest;
import com.elsprage.words.model.response.UsersWordsResponse;
import com.elsprage.words.model.response.WordResponse;
import com.elsprage.words.service.WordValidationService;
import com.elsprage.words.service.WordsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/{id}")
    public ResponseEntity<WordResponse> getWordById(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long id) {
        log.info("Get word by id: {}", id);
        final WordDTO word = wordsService.getWordById(id, token);
        final WordResponse wordResponse = new WordResponse(word);
        return ResponseEntity.ok(wordResponse);
    }

    @GetMapping("/user")
    public ResponseEntity<UsersWordsResponse> getUsersWords(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        log.info("Get words");
        final List<WordDTO> words = wordsService.getWordsForUser(token);
        final UsersWordsResponse usersWordsResponse = new UsersWordsResponse(words);
        return ResponseEntity.ok(usersWordsResponse);
    }

    @PutMapping
    public ResponseEntity<WordResponse> updateWord(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody WordRequest wordRequest) {
        log.info("Update word: {}", wordRequest);
        wordValidationService.validateWordRequest(wordRequest);
        final WordDTO savedWord = wordsService.updateWord(wordRequest, token);
        final WordResponse wordResponse = new WordResponse(savedWord);
        return ResponseEntity.ok(wordResponse);
    }

    @DeleteMapping("/{wordId}")
    public ResponseEntity<Void> deleteWord(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long wordId) {
        log.info("Delete word with id: {}", wordId);
        wordsService.deleteWord(wordId, token);
        return ResponseEntity.ok().build();
    }
}