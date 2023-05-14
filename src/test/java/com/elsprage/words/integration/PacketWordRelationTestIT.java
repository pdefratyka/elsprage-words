package com.elsprage.words.integration;

import com.elsprage.words.AbstractIT;
import com.elsprage.words.persistance.entity.Packet;
import com.elsprage.words.persistance.entity.Word;
import com.elsprage.words.persistance.repository.PacketRepository;
import com.elsprage.words.persistance.repository.WordRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PacketWordRelationTestIT extends AbstractIT {

    @Autowired
    private PacketRepository packetRepository;

    @Autowired
    private WordRepository wordRepository;

    @Test
    void shouldNotRemoveWordsOnPacketRemove() {
        // given
        final Word word = Word.builder()
                .id(1L)
                .userId(1L)
                .value("value")
                .translation("translation")
                .valueLanguageId(1L)
                .translationLanguageId(1L)
                .build();
        wordRepository.save(word);
        final Packet packet = Packet.builder()
                .id(1L)
                .userId(1L)
                .name("name")
                .translationLanguageId(1L)
                .valueLanguageId(1L)
                .words(Set.of(word))
                .build();
        packetRepository.save(packet);
        // when
        packetRepository.deleteById(1L);
        // then
        assertTrue(packetRepository.findById(1L).isEmpty());
        assertTrue(wordRepository.findById(1L).isPresent());
    }

    @Test
    void shouldNotRemoveWordsOnPacketRemove2() {
        // given
        final Word word = Word.builder()
                .id(1L)
                .userId(1L)
                .value("value")
                .translation("translation")
                .valueLanguageId(1L)
                .translationLanguageId(1L)
                .build();
        wordRepository.save(word);
        final Packet packet = Packet.builder()
                .id(1L)
                .userId(1L)
                .name("name")
                .translationLanguageId(1L)
                .valueLanguageId(1L)
                .words(Set.of(word))
                .build();
        packetRepository.save(packet);
        // when
        wordRepository.deleteById(1L);
        // then
        assertTrue(wordRepository.findById(1L).isEmpty());
        assertEquals(0, wordRepository.findAll().size());
        assertTrue(packetRepository.findById(1L).isPresent());
    }
}
