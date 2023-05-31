//package com.elsprage.words.service.impl;
//
//import com.elsprage.words.common.mapper.LanguageMapper;
//import com.elsprage.words.common.mapper.WordMapper;
//import com.elsprage.words.model.dto.WordDTO;
//import com.elsprage.words.model.request.WordRequest;
//import com.elsprage.words.persistance.entity.Word;
//import com.elsprage.words.persistance.repository.WordRepository;
//import com.elsprage.words.service.ImageService;
//import com.elsprage.words.service.JwtService;
//import com.elsprage.words.service.LanguageService;
//import com.elsprage.words.tools.utils.TokenService;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.mockito.Mockito;
//
//import java.io.IOException;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//public class WordServiceImplTest {
//
//    private WordsServiceImpl wordsService;
//    private ImageService imageService;
//    private WordRepository wordRepository;
//    private JwtService jwtService;
//    private LanguageService languageService;
//
//    @BeforeAll
//    void setup() {
//        LanguageMapper languageMapper = new LanguageMapper();
//        WordMapper wordMapper = new WordMapper(languageMapper);
//        imageService = Mockito.mock(ImageServiceImpl.class);
//        wordRepository = Mockito.mock(WordRepository.class);
//        jwtService = Mockito.mock(JwtServiceImpl.class);
//        languageService = Mockito.mock(LanguageServiceImpl.class);
//
//        wordsService = new WordsServiceImpl(wordRepository, imageService, wordMapper, jwtService, languageService);
//    }
//
//    @Test
//    void shouldSaveWord() throws IOException {
//        // given
//        final WordRequest wordRequest = getWordRequest();
//        final Long userId = 1L;
//        final byte[] imageData = new byte[4];
//        final String token = TokenService.generateTokenWithUserId(userId);
//        final Long wordId = 1L;
//        Mockito.when(languageService.getSymbolByLanguageId(wordRequest.getTranslationLanguageId())).thenReturn(Optional.of("en"));
//        Mockito.when(languageService.getSymbolByLanguageId(wordRequest.getValueLanguageId())).thenReturn(Optional.of("pl"));
//        Mockito.when(imageService.getImage(wordRequest.getValue())).thenReturn(imageData);
//        Mockito.when(jwtService.extractUserId(token)).thenReturn(userId);
//        Mockito.when(wordRepository.save(any())).thenReturn(getWord(wordRequest, userId, imageData, wordId));
//        // when
//        WordDTO wordDTO = wordsService.saveWord(wordRequest, token);
//        // then
//        assertEquals(wordRequest.getValue(), wordDTO.getValue());
//        assertEquals(wordRequest.getTranslation(), wordDTO.getTranslation());
//        assertEquals(wordRequest.getTranslationLanguageId(), wordDTO.getTranslationLanguageId());
//        assertEquals(wordRequest.getValueLanguageId(), wordDTO.getValueLanguageId());
//        assertEquals(userId, wordDTO.getUserId());
//        assertEquals(wordId, wordDTO.getId());
//    }
//
//    @Test
//    void shouldUpdateWord() throws IOException {
//        // Think about updating image. If there is encoded Data, then image should not be changed or something like that
//        // given
//        final Long wordId = 1L;
//        final WordRequest wordRequest = getWordRequest();
//        wordRequest.setId(wordId);
//        final Long userId = 1L;
//        final byte[] imageData = new byte[4];
//        final String token = TokenService.generateTokenWithUserId(userId);
//        Mockito.when(languageService.getSymbolByLanguageId(wordRequest.getTranslationLanguageId())).thenReturn(Optional.of("en"));
//        Mockito.when(languageService.getSymbolByLanguageId(wordRequest.getValueLanguageId())).thenReturn(Optional.of("pl"));
//        Mockito.when(imageService.getImage(wordRequest.getValue())).thenReturn(imageData);
//        Mockito.when(jwtService.extractUserId(token)).thenReturn(userId);
//        Mockito.when(wordRepository.findById(wordId)).thenReturn(Optional.of(getWord(wordRequest, userId, imageData, wordId)));
//        Mockito.when(wordRepository.save(any())).thenReturn(getWord(wordRequest, userId, imageData, wordId));
//        // when
//        WordDTO wordDTO = wordsService.updateWord(wordRequest, token);
//        // then
//        assertEquals(wordRequest.getValue(), wordDTO.getValue());
//        assertEquals(wordRequest.getTranslation(), wordDTO.getTranslation());
//        assertEquals(wordRequest.getTranslationLanguageId(), wordDTO.getTranslationLanguageId());
//        assertEquals(wordRequest.getValueLanguageId(), wordDTO.getValueLanguageId());
//        assertEquals(userId, wordDTO.getUserId());
//        assertEquals(wordId, wordDTO.getId());
//    }
//
//    @Test
//    void shouldDeleteWord() {
//        // given
//        final Long wordId = 1L;
//        final WordRequest wordRequest = getWordRequest();
//        wordRequest.setId(wordId);
//        final Long userId = 1L;
//        final byte[] imageData = new byte[4];
//        final String token = TokenService.generateTokenWithUserId(userId);
//        Mockito.when(jwtService.extractUserId(token)).thenReturn(userId);
//        Mockito.when(wordRepository.findById(wordId)).thenReturn(Optional.of(getWord(wordRequest, userId, imageData, wordId)));
//        Mockito.doNothing().when(wordRepository).deleteById(wordId);
//        // when
//        wordsService.deleteWord(wordId, token);
//    }
//
//    private WordRequest getWordRequest() {
//        return WordRequest
//                .builder()
//                .translationLanguageId(1L)
//                .valueLanguageId(2L)
//                .value("dog")
//                .translation("pies")
//                .build();
//    }
//
//    private Word getWord(WordRequest wordRequest, Long userId, byte[] imageData, Long wordId) {
//        return Word.builder()
//                .id(wordId)
//                .value(wordRequest.getValue())
//                .translation(wordRequest.getTranslation())
//                .valueLanguageId(wordRequest.getValueLanguageId())
//                .translationLanguageId(wordRequest.getTranslationLanguageId())
//                .example(wordRequest.getExample())
//                .image(wordRequest.getImage())
//                .sound(wordRequest.getSound())
//                .imageData(imageData)
//                .userId(userId)
//                .build();
//    }
//}
