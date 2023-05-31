package com.elsprage.words.service.impl;

import com.elsprage.words.exception.WordException;
import com.elsprage.words.persistance.entity.Word;
import com.elsprage.words.persistance.repository.WordRepository;
import com.elsprage.words.service.WordModificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WordModificationServiceImpl implements WordModificationService {

    private final WordRepository wordRepository;

    @Override
    public void setAudio(Long wordId, byte[] audioData) {
        final Word word = wordRepository.findById(wordId).orElseThrow(() -> new WordException.WordNotFound(wordId));
        word.setAudioData(audioData);
        wordRepository.save(word);
    }
}
