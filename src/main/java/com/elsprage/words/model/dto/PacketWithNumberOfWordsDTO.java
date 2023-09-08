package com.elsprage.words.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
@AllArgsConstructor
public class PacketWithNumberOfWordsDTO {
    Long id;
    String name;
    Long valueLanguageId;
    Long translationLanguageId;
    String description;
    LanguageDTO valueLanguage;
    LanguageDTO translationLanguage;
    List<Long> wordsIds;
}
