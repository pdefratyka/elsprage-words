package com.elsprage.words.service.impl;

import com.elsprage.words.exception.LanguageDoesNotExists;
import com.elsprage.words.kafka.producer.AudioKafkaMessageProducerService;
import com.elsprage.words.persistance.entity.Word;
import com.elsprage.words.service.AudioService;
import com.elsprage.words.service.LanguageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class AudioServiceImpl implements AudioService {

    private final AudioKafkaMessageProducerService audioKafkaMessageProducerService;
    private final LanguageService languageService;

    @Override
    public void findAudioForWord(Word word) {
        log.info("Sending event to find audio for word: {}", word);
        sendAudioEvent(word);
    }

    private void sendAudioEvent(final Word word) {
        try {
            final String language = languageService.getSymbolByLanguageId(word.getValueLanguageId())
                    .orElseThrow(() -> new LanguageDoesNotExists(word.getValueLanguageId()));
            audioKafkaMessageProducerService.sendMessage(word.getId(), word.getValue(), language);
        } catch (Exception e) {
            log.error("Error while sending message to kafka", e);
        }
    }
}
