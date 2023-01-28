package com.elsprage.words.service.impl;

import com.elsprage.words.common.mapper.WordMapper;
import com.elsprage.words.exception.LanguageDoesNotExists;
import com.elsprage.words.model.dto.WordDTO;
import com.elsprage.words.model.request.WordRequest;
import com.elsprage.words.persistance.entity.Word;
import com.elsprage.words.persistance.repository.WordRepository;
import com.elsprage.words.service.ImageService;
import com.elsprage.words.service.JwtService;
import com.elsprage.words.service.LanguageService;
import com.elsprage.words.service.WordsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class WordsServiceImpl implements WordsService {

    private static final String EN_SYMBOL = "en";
    private final WordRepository wordRepository;
    private final ImageService imageService;
    private final WordMapper wordMapper;
    private final JwtService jwtService;
    private final LanguageService languageService;

    @Override
    public WordDTO saveWord(WordRequest wordRequest, String token) {
        final byte[] imageData = getImageData(wordRequest);
        final Long userId = jwtService.extractUserId(token);
        final WordDTO wordDTO = wordMapper.mapToWordDTO(wordRequest, userId, imageData);
        final Word savedWord = wordRepository.save(wordMapper.mapToWord(wordDTO));
        return wordMapper.mapToWordDTO(savedWord);
    }

    @Override
    public List<WordDTO> getAllWords() {
        final List<Word> words = wordRepository.findAll();
        return wordMapper.mapToWordsDTO(words);
    }

    private byte[] getImageData(WordRequest wordRequest) {
        if (!ObjectUtils.isEmpty(wordRequest.getImage())) {
            return getImageFromUrl(wordRequest.getImage());
        } else {
            return getImageByKeyword(wordRequest);
        }
    }

    private byte[] getImageFromUrl(String image) {
        if (!ObjectUtils.isEmpty(image)) {
            try {
                return imageService.getImageFromUrl(image);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    private byte[] getImageByKeyword(WordRequest wordRequest) {
        final String keyword = getKeywordFromImage(wordRequest);
        if (!ObjectUtils.isEmpty(keyword)) {
            try {
                return imageService.getImage(keyword);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    private String getKeywordFromImage(WordRequest wordRequest) {
        String translationLanguageSymbol = languageService.getSymbolByLanguageId(wordRequest.getTranslationLanguageId())
                .orElseThrow(() -> new LanguageDoesNotExists(wordRequest.getTranslationLanguageId()));
        String valueLanguageSymbol = languageService.getSymbolByLanguageId(wordRequest.getValueLanguageId())
                .orElseThrow(() -> new LanguageDoesNotExists(wordRequest.getValueLanguageId()));
        if (EN_SYMBOL.equals(translationLanguageSymbol)) {
            return wordRequest.getTranslation();
        } else if (EN_SYMBOL.equals(valueLanguageSymbol)) {
            return wordRequest.getValue();
        }
        return null;
    }
}
