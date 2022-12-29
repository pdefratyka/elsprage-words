package com.elsprage.words.service;

import com.elsprage.words.model.dto.LanguageDTO;

import java.util.List;

public interface LanguageService {
    List<LanguageDTO> getLanguages();
}
