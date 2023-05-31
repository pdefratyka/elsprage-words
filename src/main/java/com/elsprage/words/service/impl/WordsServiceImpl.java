package com.elsprage.words.service.impl;

import com.elsprage.words.common.mapper.WordMapper;
import com.elsprage.words.exception.LanguageDoesNotExists;
import com.elsprage.words.exception.WordException;
import com.elsprage.words.model.dto.WordDTO;
import com.elsprage.words.model.request.WordRequest;
import com.elsprage.words.model.response.UsersWordsResponse;
import com.elsprage.words.persistance.entity.Word;
import com.elsprage.words.persistance.repository.WordRepository;
import com.elsprage.words.service.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
@Log4j2
@AllArgsConstructor
public class WordsServiceImpl implements WordsService {

    private static final String EN_SYMBOL = "en";
    private final WordRepository wordRepository;
    private final ImageService imageService;
    private final WordMapper wordMapper;
    private final JwtService jwtService;
    private final LanguageService languageService;
    private final WordAdditionalInfoService wordAdditionalInfoService;

    @Override
    public WordDTO saveWord(WordRequest wordRequest, String token) {
        final byte[] imageData = getImageData(wordRequest);
        final Long userId = jwtService.extractUserId(token);
        final WordDTO wordDTO = wordMapper.mapToWordDTO(wordRequest, userId, imageData);
        final Word savedWord = saveWord(wordDTO);
        wordAdditionalInfoService.setAdditionalInfo(savedWord);
        return wordMapper.mapToWordDTO(savedWord);
    }

    private Word saveWord(final WordDTO wordDTO) {
        try {
            return wordRepository.save(wordMapper.mapToWord(wordDTO));
        } catch (Exception ex) {
            if (ex.getMessage().contains("constraint [unique_value_with_value_language_id]")) {
                throw new WordException.WordAlreadyExistException(ex);
            }
            throw new RuntimeException(ex);
        }
    }

    @Override
    public UsersWordsResponse getWordsForUser(String token, String query, int page, int pageSize) {
        final Long userId = jwtService.extractUserId(token);
        log.info("Get words for user with id: {}, query: {}, page: {}, pageSize:{}", userId, query, page, pageSize);
        final List<Word> words = wordRepository.findByUserId(userId, query.toLowerCase(), PageRequest.of(page, pageSize));
        final BigDecimal size = wordRepository.findSizeOfListOfWords(userId, query.toLowerCase());
        return new UsersWordsResponse(wordMapper.mapToWordsDTO(words), size, page, pageSize);
    }

    @Override
    public WordDTO getWordById(Long wordId, String token) {
        final Long userId = jwtService.extractUserId(token);
        log.info("Get word with id: {} and user id: {}", wordId, userId);
        final Word word = wordRepository.findById(wordId)
                .orElseThrow(() -> new WordException.WordNotFound(wordId));
        if (!word.getUserId().equals(userId)) {
            throw new WordException.WrongUserId("Word with given id is not assigned to this user");
        }
        return wordMapper.mapToWordDTO(word);
    }

    @Override
    public WordDTO updateWord(WordRequest wordRequest, String token) {
        final Word word = wordRepository.findById(wordRequest.getId())
                .orElseThrow(() -> new WordException.WordNotFound(wordRequest.getId()));
        final Long userId = jwtService.extractUserId(token);
        if (word.getUserId().equals(userId)) {
            return saveWord(wordRequest, token);
        } else {
            throw new WordException.WrongUserId("User is not allowed to update word with id: " + wordRequest.getId());
        }
    }

    @Override
    @Transactional
    public void deleteWord(Long wordId, String token) {
        final Word word = wordRepository.findById(wordId)
                .orElseThrow(() -> new WordException.WordNotFound(wordId));
        final Long userId = jwtService.extractUserId(token);
        if (word.getUserId().equals(userId)) {
            wordRepository.deletePacketsWordsByWordId(wordId);
            wordRepository.deleteById(wordId);
        } else {
            throw new WordException.WrongUserId("User is not allowed to delete word with id: " + wordId);
        }
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
            return wordRequest.getTranslation().split(";")[0];
        } else if (EN_SYMBOL.equals(valueLanguageSymbol)) {
            return wordRequest.getValue().split(";")[0];
        }
        return null;
    }
}
