package com.elsprage.words.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PacketRequest {
    private Long id;
    private String name;
    private Set<Long> wordsIds;
    private Long valueLanguageId;
    private Long translationLanguageId;
    private String description;
}
