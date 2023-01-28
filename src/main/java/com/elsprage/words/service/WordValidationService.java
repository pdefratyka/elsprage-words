package com.elsprage.words.service;

import com.elsprage.words.model.request.WordRequest;

public interface WordValidationService {
    void validateWordRequest(WordRequest wordRequest);
}
