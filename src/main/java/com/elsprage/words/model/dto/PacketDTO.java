package com.elsprage.words.model.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class PacketDTO {
    Long id;
    String name;
    List<WordDTO> words;
    List<Long> wordsIds;
    Long valueLanguageId;
    Long translationLanguageId;
    String description;
}
