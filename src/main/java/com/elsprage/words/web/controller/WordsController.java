package com.elsprage.words.web.controller;

import com.elsprage.words.model.dto.WordDTO;
import com.elsprage.words.model.request.WordRequest;
import com.elsprage.words.model.response.UsersWordsResponse;
import com.elsprage.words.model.response.WordResponse;
import com.elsprage.words.service.WordsService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/words")
@AllArgsConstructor
@Log4j2
public class WordsController {

    private final WordsService wordsService;

    @PostMapping
    public ResponseEntity<WordResponse> saveWord(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @Validated @RequestBody WordRequest wordRequest) {
        log.info("Save word: {}", wordRequest);
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
    public ResponseEntity<UsersWordsResponse> getUsersWords(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                            @RequestParam(defaultValue = "") String query,
                                                            @RequestParam(defaultValue = "0") Integer page,
                                                            @RequestParam(defaultValue = "20") Integer pageSize) {
        log.info("Get words for query: {}", query);
        return ResponseEntity.ok(wordsService.getWordsForUser(token, query, page, pageSize));
    }

    @PutMapping
    public ResponseEntity<WordResponse> updateWord(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody @Validated WordRequest wordRequest) {
        log.info("Update word: {}", wordRequest);
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