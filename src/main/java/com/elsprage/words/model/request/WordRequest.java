package com.elsprage.words.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WordRequest {
    private Long id;
    private String value;
    private String translation;
    private Long valueLanguageId;
    private Long translationLanguageId;
    private String image;
    private String sound;
    private String example;
}
