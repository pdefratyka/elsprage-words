package com.elsprage.words.model.response;

import com.elsprage.words.model.dto.WordDTO;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
public class UsersWordsResponse {
    List<WordDTO> words;
    BigDecimal numberOfWords;
    int page;
    int pageSize;
}
