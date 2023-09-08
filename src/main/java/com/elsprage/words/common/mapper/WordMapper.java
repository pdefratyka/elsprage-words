package com.elsprage.words.common.mapper;

import com.elsprage.words.model.dto.WordDTO;
import com.elsprage.words.model.request.WordRequest;
import com.elsprage.words.persistance.entity.Word;
import com.elsprage.words.utils.ImageUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WordMapper {

    private final LanguageMapper languageMapper;

    public WordDTO mapToWordDTO(final Word word) {
        final String encodedImage = ImageUtils.encodeImage(word.getImageData());
        final String encodedAudio = ImageUtils.encodeImage(word.getAudioData());

        final String exampleAudioEncoded = ImageUtils.encodeImage(word.getExampleAudio());
        final String explanationAudioEncoded = ImageUtils.encodeImage(word.getExplanationAudio());
        final String translationAudioEncoded = ImageUtils.encodeImage(word.getTranslationAudio());

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
                .audioDataEncoded(encodedAudio)
//                .audioData(word.getAudioData())
                .explanation(word.getExplanation())
//                .explanationAudio(word.getExplanationAudio())
//                .translationAudio(word.getTranslationAudio())
//                .exampleAudio(word.getExampleAudio())
                .exampleAudioEncoded(exampleAudioEncoded)
                .explanationAudioEncoded(explanationAudioEncoded)
                .translationAudioEncoded(translationAudioEncoded)
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
                .audioData(wordDTO.getAudioData())
                .explanation(wordDTO.getExplanation())
                .explanationAudio(wordDTO.getExplanationAudio())
                .translationAudio(wordDTO.getTranslationAudio())
                .exampleAudio(wordDTO.getExampleAudio())
                .build();
    }

    public WordDTO mapToWordDTO(final WordRequest wordRequest, Long userId) {
        return WordDTO.builder()
                .id(wordRequest.getId())
                .value(wordRequest.getValue())
                .translation(wordRequest.getTranslation())
                .valueLanguageId(wordRequest.getValueLanguageId())
                .translationLanguageId(wordRequest.getTranslationLanguageId())
                .example(wordRequest.getExample())
                .image(wordRequest.getImage())
                .sound(wordRequest.getSound())
                .userId(userId)
                .explanation(wordRequest.getExplanation())
                .explanationAudio(wordRequest.getExplanationAudio())
                .translationAudio(wordRequest.getTranslationAudio())
                .exampleAudio(wordRequest.getExampleAudio())
                .audioData(wordRequest.getAudioData())
                .build();
    }

    public List<WordDTO> mapToWordsDTO(final List<Word> words) {
        return words.stream()
                .map(this::mapToWordDTO)
                .collect(Collectors.toList());
    }
}
