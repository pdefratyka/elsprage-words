package com.elsprage.words.service;

import com.elsprage.words.model.dto.LanguageDTO;

import java.util.List;
import java.util.Optional;

public interface LanguageService {
    List<LanguageDTO> getLanguages();

    Optional<String> getSymbolByLanguageId(Long id);
}
