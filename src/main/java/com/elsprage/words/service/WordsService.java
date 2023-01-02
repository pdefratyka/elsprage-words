package com.elsprage.words.service;

import com.elsprage.words.model.dto.WordDTO;

import java.io.IOException;
import java.util.List;

public interface WordsService {
    WordDTO saveWord(WordDTO word);

    List<WordDTO> getAllWords();
}
