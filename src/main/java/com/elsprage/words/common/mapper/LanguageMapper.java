package com.elsprage.words.common.mapper;

import com.elsprage.words.model.dto.LanguageDTO;
import com.elsprage.words.persistance.entity.Language;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LanguageMapper {

    public LanguageDTO mapToLanguageDTO(final Language language) {
        if (language == null) {
            return null;
        }
        return LanguageDTO.builder()
                .id(language.getId())
                .name(language.getName())
                .symbol(language.getSymbol())
                .build();
    }

    public List<LanguageDTO> mapToLanguagesDTO(final List<Language> languages) {
        return languages.stream()
                .map(this::mapToLanguageDTO)
                .collect(Collectors.toList());
    }
}
