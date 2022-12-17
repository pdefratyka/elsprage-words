package com.elsprage.words.service.impl;

import com.elsprage.words.common.mapper.WordMapper;
import com.elsprage.words.model.dto.WordDTO;
import com.elsprage.words.persistance.entity.Word;
import com.elsprage.words.persistance.repository.WordRepository;
import com.elsprage.words.service.WordsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WordsServiceImpl implements WordsService {

    private final WordRepository wordRepository;
    private final WordMapper wordMapper;

    @Override
    public WordDTO saveWord(final WordDTO word) {
        Word savedWord = wordRepository.save(wordMapper.mapToWord(word));
        return wordMapper.mapToWordDTO(savedWord);
    }
}
