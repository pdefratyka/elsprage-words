package com.elsprage.words.kafka.listener;

import com.elsprage.external.words.avro.WordModificationActionType;
import com.elsprage.external.words.avro.WordModificationEvent;
import com.elsprage.words.kafka.config.KafkaConstants;
import com.elsprage.words.service.WordModificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class WordModificationListenerService {

    private final WordModificationService wordModificationService;

    @KafkaListener(topics = KafkaConstants.WORD_MODIFICATION_TOPIC, groupId = KafkaConstants.GROUP_ID)
    public void consumeMessage(WordModificationEvent message) {
        log.info("Received message for word: {}, audio:{}, image:{}", message.getWordId(),
                message.getAudioFile() != null, message.getImageFile() != null);
        if (message.getActionType().equals(WordModificationActionType.AUDIO_UPDATE)) {
            log.info("Process audio update");
            wordModificationService.setAudio(message.getWordId(), message.getAudioFile().array());
        } else if (message.getActionType().equals(WordModificationActionType.IMAGE_UPDATE)) {
            log.info("Process image update");
            wordModificationService.setImage(message.getWordId(), message.getImageFile().array());
        }
    }
}
