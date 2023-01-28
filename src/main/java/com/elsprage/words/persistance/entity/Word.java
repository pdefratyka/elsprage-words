package com.elsprage.words.persistance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
}
