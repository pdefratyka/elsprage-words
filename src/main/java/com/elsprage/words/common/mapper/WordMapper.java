package com.elsprage.words.common.mapper;

import com.elsprage.words.model.dto.WordDTO;
import com.elsprage.words.persistance.entity.Word;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WordMapper {

    private final LanguageMapper languageMapper;

    public WordDTO mapToWordDTO(final Word word) {
        final String encodedImage = getEncodedImage(word);
        return WordDTO.builder()
                .id(word.getId())
                .value(word.getValue())
                .translation(word.getTranslation())
                .translationLanguageId(word.getTranslationLanguageId())
                .valueLanguageId(word.getValueLanguageId())
                .userId(word.getUserId())
                .example(word.getExample())
                .image(word.getImage())
                .sound(word.getSound())
                .translationLanguage(languageMapper.mapToLanguageDTO(word.getTranslationLanguage()))
                .valueLanguage(languageMapper.mapToLanguageDTO(word.getValueLanguage()))
                .imageDataEncoded(encodedImage)
                .build();
    }

    public Word mapToWord(final WordDTO wordDTO) {
        return Word.builder()
                .id(wordDTO.getId())
                .value(wordDTO.getValue())
                .translation(wordDTO.getTranslation())
                .valueLanguageId(wordDTO.getValueLanguageId())
                .translationLanguageId(wordDTO.getTranslationLanguageId())
                .userId(wordDTO.getUserId())
                .example(wordDTO.getExample())
                .image(wordDTO.getImage())
                .sound(wordDTO.getSound())
                .imageData(wordDTO.getImageData())
                .build();
    }

    public List<WordDTO> mapToWordsDTO(final List<Word> words) {
        return words.stream()
                .map(this::mapToWordDTO)
                .collect(Collectors.toList());
    }

    private String getEncodedImage(final Word word) {
        if (word.getImageData() != null) {
            return Base64.getEncoder().encodeToString(word.getImageData());
        }
        return "";
    }
}
