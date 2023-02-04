package com.elsprage.words.service;

import com.elsprage.words.model.dto.WordDTO;
import com.elsprage.words.model.request.WordRequest;

import java.util.List;

public interface WordsService {
    WordDTO saveWord(WordRequest wordRequest, String token);

    List<WordDTO> getWordsForUser(String token);

    WordDTO updateWord(WordRequest wordRequest, String token);

    void deleteWord(Long wordId, String token);
}
