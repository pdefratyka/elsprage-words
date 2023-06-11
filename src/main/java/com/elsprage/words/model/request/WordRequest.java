package com.elsprage.words.model.request;

import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private String value;
    @NotNull
    private String translation;
    @NotNull
    private Long valueLanguageId;
    @NotNull
    private Long translationLanguageId;
    private String image;
    private String sound;
    private String example;
    private String explanation;
}
