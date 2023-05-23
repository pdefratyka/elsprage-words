package com.elsprage.words.web.controller;

import com.elsprage.words.model.response.LanguagesResponse;
import com.elsprage.words.service.LanguageService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/languages")
@AllArgsConstructor
@Log4j2
public class LanguageController {

    private final LanguageService languageService;

    @GetMapping
    public ResponseEntity<LanguagesResponse> getLanguages() {
        log.info("Get languages");
        final LanguagesResponse languagesResponse = new LanguagesResponse(languageService.getLanguages());
        return ResponseEntity.ok(languagesResponse);
    }
}
