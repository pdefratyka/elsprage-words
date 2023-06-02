package com.elsprage.words.kafka.producer;

import com.elsprage.external.words.avro.WordImageEvent;
import com.elsprage.words.kafka.config.KafkaConstants;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ImageKafkaMessageProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(final Long wordId, final String key, final String language) {
        final WordImageEvent wordImageEvent = new WordImageEvent(wordId, key, language);
        kafkaTemplate.send(KafkaConstants.IMAGE_TOPIC, wordImageEvent);
    }
}
