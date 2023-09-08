package com.elsprage.words.service.impl;

import com.elsprage.words.common.mapper.WordMapper;
import com.elsprage.words.exception.WordException;
import com.elsprage.words.model.dto.WordDTO;
import com.elsprage.words.model.request.WordRequest;
import com.elsprage.words.model.request.WordUpdateRequest;
import com.elsprage.words.model.response.UsersWordsResponse;
import com.elsprage.words.persistance.entity.Word;
import com.elsprage.words.persistance.repository.WordRepository;
import com.elsprage.words.service.JwtService;
import com.elsprage.words.service.WordAdditionalInfoService;
import com.elsprage.words.service.WordsService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Log4j2
@AllArgsConstructor
public class WordsServiceImpl implements WordsService {

    private final WordRepository wordRepository;
    private final WordMapper wordMapper;
    private final JwtService jwtService;
    private final WordAdditionalInfoService wordAdditionalInfoService;

    @Override
    public WordDTO saveWord(WordRequest wordRequest, String token) {
        final Long userId = jwtService.extractUserId(token);
        final WordDTO wordDTO = wordMapper.mapToWordDTO(wordRequest, userId);
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
            throw new WordException("Unexpected exception", HttpStatus.INTERNAL_SERVER_ERROR, ex);
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
    public WordDTO updateWord(WordUpdateRequest wordRequest, String token) {
        final Word word = wordRepository.findById(wordRequest.getId())
                .orElseThrow(() -> new WordException.WordNotFound(wordRequest.getId()));
        final Long userId = jwtService.extractUserId(token);
        if (word.getUserId().equals(userId)) {
            word.setValue(wordRequest.getValue());
            word.setTranslation(wordRequest.getTranslation());
            Word savedWord = wordRepository.save(word);
            return wordMapper.mapToWordDTO(savedWord);
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
}
