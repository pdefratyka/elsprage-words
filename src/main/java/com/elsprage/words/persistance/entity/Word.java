package com.elsprage.words.persistance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "words")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String value;
    @Column
    private String translation;
    @ManyToOne
    @JoinColumn(name = "value_language_id", nullable = false, insertable = false, updatable = false)
    private Language valueLanguage;
    @Column(name = "value_language_id")
    private Long valueLanguageId;
    @ManyToOne
    @JoinColumn(name = "translation_language_id", nullable = false, insertable = false, updatable = false)
    private Language translationLanguage;
    @Column(name = "translation_language_id")
    private Long translationLanguageId;
    @Column
    private String image;
    @Column
    private String sound;
    @Column
    private String example;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "image_data")
    private byte[] imageData;
    @Column(name = "audio_data")
    private byte[] audioData;
    @Column(name = "explanation")
    private String explanation;
    @Column(name = "example_audio")
    private byte[] exampleAudio;
    @Column(name = "translation_audio")
    private byte[] translationAudio;
    @Column(name = "explanation_audio")
    private byte[] explanationAudio;

    public String toString() {
        return "Word(id=" + this.getId() + ", value=" + this.getValue() + ", translation=" + this.getTranslation() + ", valueLanguage=" + this.getValueLanguage() + ", valueLanguageId=" + this.getValueLanguageId() + ", translationLanguage=" + this.getTranslationLanguage() + ", translationLanguageId=" + this.getTranslationLanguageId() + ", image=" + this.getImage() + ", sound=" + this.getSound() + ", example=" + this.getExample() + ", userId=" + this.getUserId() + ")";
    }
}
