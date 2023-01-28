package com.elsprage.words.service.impl;

import com.elsprage.words.exception.WordRequestValidationException;
import com.elsprage.words.model.request.WordRequest;
import com.elsprage.words.service.WordValidationService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class WordValidationServiceImpl implements WordValidationService {
    @Override
    public void validateWordRequest(final WordRequest wordRequest) {
        if (ObjectUtils.isEmpty(wordRequest.getValue())) {
            throw new WordRequestValidationException("Value cannot be empty");
        }
        if (ObjectUtils.isEmpty(wordRequest.getTranslation())) {
            throw new WordRequestValidationException("Translation cannot be empty");
        }
        if (ObjectUtils.isEmpty(wordRequest.getValueLanguageId())) {
            throw new WordRequestValidationException("Value language cannot be empty");
        }
        if (ObjectUtils.isEmpty(wordRequest.getTranslationLanguageId())) {
            throw new WordRequestValidationException("Translation language cannot be empty");
        }
    }
}
