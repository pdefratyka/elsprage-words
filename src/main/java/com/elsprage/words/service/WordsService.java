package com.elsprage.words.service;

import com.elsprage.words.model.dto.WordDTO;
import com.elsprage.words.model.request.WordRequest;
import com.elsprage.words.model.request.WordUpdateRequest;
import com.elsprage.words.model.response.UsersWordsResponse;

public interface WordsService {
    WordDTO saveWord(WordRequest wordRequest, String token);

    UsersWordsResponse getWordsForUser(String token, String query, int page, int pageSize);

    WordDTO getWordById(Long wordId, String token);

    WordDTO updateWord(WordRequest wordRequest, String token);

    WordDTO updateWord(WordUpdateRequest wordRequest, String token);

    void deleteWord(Long wordId, String token);


}
