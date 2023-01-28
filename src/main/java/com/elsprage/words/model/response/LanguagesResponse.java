package com.elsprage.words.model.response;

import com.elsprage.words.model.dto.LanguageDTO;
import lombok.Value;

import java.util.List;

@Value
public class LanguagesResponse {
    List<LanguageDTO> languages;
}
