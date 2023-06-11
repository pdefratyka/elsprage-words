package com.elsprage.words.web.controller;

import com.elsprage.words.AbstractControllerTest;
import com.elsprage.words.common.constants.ExceptionConstants;
import com.elsprage.words.helper.JsonMapper;
import com.elsprage.words.model.dto.WordDTO;
import com.elsprage.words.model.request.WordRequest;
import com.elsprage.words.service.WordsService;
import com.elsprage.words.service.impl.WordsServiceImpl;
import com.elsprage.words.tools.utils.TokenService;
import com.elsprage.words.web.exception.ControllerAdvice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class WordControllerTest extends AbstractControllerTest {
    private static final String BASE_URL = "/words";

    private MockMvc mockMvc;
    private WordsService wordsService;

    private String token;

    @BeforeEach
    void setup() {
        wordsService = Mockito.mock(WordsServiceImpl.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new WordsController(wordsService))
                .setControllerAdvice(new ControllerAdvice())
                .build();
        token = TokenService.generateToken();
    }

    @Test
    void shouldSaveWord() throws Exception {
        // given
        final WordRequest wordRequest = WordRequest
                .builder()
                .value("dog")
                .translation("pies")
                .translationLanguageId(1L)
                .valueLanguageId(1L)
                .build();
        final String requestBodyAsJson = JsonMapper.mapObjectToJson(wordRequest);
        final WordDTO wordDTO = WordDTO.builder()
                .id(1L)
                .userId(1L)
                .value("dog")
                .translation("pies")
                .build();
        Mockito.when(wordsService.saveWord(wordRequest, token)).thenReturn(wordDTO);
        // when
        ResultActions resultActions = mockMvc.perform(post(BASE_URL)
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBodyAsJson)
                .characterEncoding("utf-8"));
        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("word.value",
                        is(wordDTO.getValue())))
                .andExpect(jsonPath("word.translation",
                        is(wordDTO.getTranslation())))
                .andExpect(jsonPath("word.id",
                        is(wordDTO.getId().intValue())))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturnBadRequestWhenMissingRequiredArgumentOnWordSave() throws Exception {
        // given
        final WordRequest wordRequest = WordRequest
                .builder()
                .translation("pies")
                .translationLanguageId(1L)
                .valueLanguageId(1L)
                .build();
        final String requestBodyAsJson = JsonMapper.mapObjectToJson(wordRequest);
        // when
        ResultActions resultActions = mockMvc.perform(post(BASE_URL)
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBodyAsJson)
                .characterEncoding("utf-8"));
        // then
        resultActions.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturnBadRequestWhenMissingBodyOnWordSave() throws Exception {
        // when
        ResultActions resultActions = mockMvc.perform(post(BASE_URL)
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"));
        // then
        resultActions.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturnInternalServerErrorWhenUnexpectedErrorOccurOnSaveWord() throws Exception {
        // given
        final WordRequest wordRequest = WordRequest
                .builder()
                .value("dog")
                .translation("pies")
                .translationLanguageId(1L)
                .valueLanguageId(1L)
                .build();
        final String requestBodyAsJson = JsonMapper.mapObjectToJson(wordRequest);
        Mockito.doThrow(new RuntimeException())
                .when(wordsService)
                .saveWord(wordRequest, token);
        // when
        ResultActions resultActions = mockMvc.perform(post(BASE_URL)
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBodyAsJson)
                .characterEncoding("utf-8"));
        // then
        resultActions.andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath(ExceptionConstants.TIME,
                        is(notNullValue())))
                .andExpect(jsonPath(ExceptionConstants.HTTP_STATUS,
                        is(500)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldDeleteWord() throws Exception {
        // given
        final Long wordId = 1L;
        Mockito.doNothing().when(wordsService).deleteWord(wordId, token);
        // when
        ResultActions resultActions = mockMvc.perform(delete(BASE_URL + "/" + wordId)
                .header(HttpHeaders.AUTHORIZATION, token));
        // then
        resultActions.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateWord() throws Exception {
        // given
        final WordRequest wordRequest = WordRequest
                .builder()
                .id(1L)
                .value("dog")
                .translation("pies")
                .translationLanguageId(1L)
                .valueLanguageId(1L)
                .build();
        final String requestBodyAsJson = JsonMapper.mapObjectToJson(wordRequest);
        final WordDTO wordDTO = WordDTO.builder()
                .id(1L)
                .userId(1L)
                .value("dog")
                .translation("pies")
                .build();
        Mockito.when(wordsService.updateWord(wordRequest, token)).thenReturn(wordDTO);
        // when
        ResultActions resultActions = mockMvc.perform(put(BASE_URL)
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBodyAsJson)
                .characterEncoding("utf-8"));
        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("word.value",
                        is(wordDTO.getValue())))
                .andExpect(jsonPath("word.translation",
                        is(wordDTO.getTranslation())))
                .andExpect(jsonPath("word.id",
                        is(wordDTO.getId().intValue())))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldThrowBadRequestExceptionCauseMissingArgumentInRequestBody() throws Exception {
        // given
        WordRequest wordRequest = WordRequest
                .builder()
                .build();
        String requestBodyAsJson = JsonMapper.mapObjectToJson(wordRequest);

        Mockito.doThrow(new RuntimeException())
                .when(wordsService)
                .updateWord(wordRequest, token);

        // when
        ResultActions resultActions = mockMvc.perform(post(BASE_URL)
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBodyAsJson)
                .characterEncoding("utf-8"));
        // then
        resultActions.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(ExceptionConstants.TIME,
                        is(notNullValue())))
                .andExpect(jsonPath(ExceptionConstants.HTTP_STATUS,
                        is(400)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
