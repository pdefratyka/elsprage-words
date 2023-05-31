package com.elsprage.words.kafka.producer;

import com.elsprage.external.words.avro.WordAudioEvent;
import com.elsprage.words.kafka.config.KafkaConstants;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AudioKafkaMessageProducerService {

    private final KafkaTemplate<String, WordAudioEvent> kafkaTemplate;

    public void sendMessage(final Long wordId, final String key, final String language) {
        final WordAudioEvent wordAudioEvent = new WordAudioEvent(wordId, key, language);
        kafkaTemplate.send(KafkaConstants.AUDIO_TOPIC, wordAudioEvent);
    }
}
