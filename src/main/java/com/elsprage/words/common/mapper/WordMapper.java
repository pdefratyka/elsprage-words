package com.elsprage.words.common.mapper;

import com.elsprage.words.model.dto.WordDTO;
import com.elsprage.words.persistance.entity.Word;
import org.springframework.stereotype.Service;

@Service
public class WordMapper {

    public WordDTO mapToWordDTO(final Word word) {
        return WordDTO.builder()
                .id(word.getId())
                .value(word.getValue())
                .translation(word.getTranslation())
                .build();
    }

    public Word mapToWord(final WordDTO wordDTO) {
        return Word.builder()
                .id(wordDTO.getId())
                .value(wordDTO.getValue())
                .translation(wordDTO.getTranslation())
                .build();
    }
}
