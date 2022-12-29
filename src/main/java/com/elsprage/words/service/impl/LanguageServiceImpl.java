package com.elsprage.words.service.impl;

import com.elsprage.words.common.mapper.LanguageMapper;
import com.elsprage.words.model.dto.LanguageDTO;
import com.elsprage.words.persistance.repository.LanguageRepository;
import com.elsprage.words.service.LanguageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;
    private final LanguageMapper languageMapper;

    @Override
    public List<LanguageDTO> getLanguages() {
        return languageMapper.mapToLanguagesDTO(languageRepository.findAll());
    }
}
