package com.elsprage.words.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WordDTO {
    private Long id;
    private String value;
    private String translation;
    private Long userId;
    private Long valueLanguageId;
    private Long translationLanguageId;
    private LanguageDTO valueLanguage;
    private LanguageDTO translationLanguage;
    private String image;
    private String sound;
    private String example;
    private String imageDataEncoded;
    private String audioDataEncoded;
    private byte[] imageData;
    private byte[] audioData;

    public String toString() {
        return "WordDTO(id=" + this.getId() + ", value=" + this.getValue() + ", translation=" + this.getTranslation() + ", userId=" + this.getUserId() + ", valueLanguageId=" + this.getValueLanguageId() + ", translationLanguageId=" + this.getTranslationLanguageId() + ", valueLanguage=" + this.getValueLanguage() + ", translationLanguage=" + this.getTranslationLanguage() + ", image=" + this.getImage() + ", sound=" + this.getSound() + ", example=" + this.getExample() + ", imageDataEncoded=" + this.getImageDataEncoded() + ")";
    }
}
