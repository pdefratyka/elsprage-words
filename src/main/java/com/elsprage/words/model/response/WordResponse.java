package com.elsprage.words.model.response;

import com.elsprage.words.model.dto.WordDTO;
import lombok.Value;

@Value
public class WordResponse {
    WordDTO word;
}
