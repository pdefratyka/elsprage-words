package com.elsprage.words.service.impl;

import com.elsprage.words.exception.LanguageDoesNotExists;
import com.elsprage.words.kafka.producer.ImageKafkaMessageProducerService;
import com.elsprage.words.persistance.entity.Word;
import com.elsprage.words.service.ImageService;
import com.elsprage.words.service.LanguageService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
public class ImageServiceImpl implements ImageService {

    private static final String EN_SYMBOL = "en";

    private final ImageKafkaMessageProducerService imageKafkaMessageProducerService;
    private final LanguageService languageService;

    @Override
    public void findImageForWord(Word word) {
        log.info("Sending event to find image for word: {}", word);
        final String keyword = getKeywordFromImage(word);
        if (!StringUtils.isBlank(keyword)) {
            sendAudioEvent(word.getId(), keyword, word.getValueLanguageId());
        }
    }

    private void sendAudioEvent(final Long wordId, final String keyword, final Long languageId) {
        try {
            final String language = languageService.getSymbolByLanguageId(languageId)
                    .orElseThrow(() -> new LanguageDoesNotExists(languageId));
            imageKafkaMessageProducerService.sendMessage(wordId, keyword, language);
        } catch (Exception e) {
            log.error("Error while sending message to kafka", e);
        }
    }

    private String getKeywordFromImage(Word word) {
        String translationLanguageSymbol = languageService.getSymbolByLanguageId(word.getTranslationLanguageId())
                .orElseThrow(() -> new LanguageDoesNotExists(word.getTranslationLanguageId()));
        String valueLanguageSymbol = languageService.getSymbolByLanguageId(word.getValueLanguageId())
                .orElseThrow(() -> new LanguageDoesNotExists(word.getValueLanguageId()));
        if (EN_SYMBOL.equals(translationLanguageSymbol)) {
            return word.getTranslation().split(";")[0];
        } else if (EN_SYMBOL.equals(valueLanguageSymbol)) {
            return word.getValue().split(";")[0];
        }
        return null;
    }
}
