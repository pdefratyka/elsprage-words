package com.elsprage.words.web.controller;

import com.elsprage.words.AbstractControllerTest;
import com.elsprage.words.exception.WordRequestValidationException;
import com.elsprage.words.helper.JsonMapper;
import com.elsprage.words.model.request.WordRequest;
import com.elsprage.words.service.WordValidationService;
import com.elsprage.words.service.WordsService;
import com.elsprage.words.service.impl.WordValidationServiceImpl;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class WordControllerTest extends AbstractControllerTest {
    private static final String BASE_URL = "/words";

    private MockMvc mockMvc;

    private WordsService wordsService;
    private WordValidationService wordValidationService;

    @BeforeEach
    void setup() {
        wordsService = Mockito.mock(WordsServiceImpl.class);
        wordValidationService = Mockito.mock(WordValidationServiceImpl.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new WordsController(wordsService, wordValidationService))
                .setControllerAdvice(new ControllerAdvice())
                .build();
    }

    @Test
    void shouldThrowBadRequestExceptionCauseMissingArgumentInRequestBody() throws Exception {
        // given
        WordRequest wordRequest = WordRequest
                .builder()
                .build();
        String requestBodyAsJson = JsonMapper.mapObjectToJson(wordRequest);
        String message = "Value input cannot be empty";

        Mockito.doThrow(new WordRequestValidationException(message))
                .when(wordValidationService)
                .validateWordRequest(wordRequest);

        // when
        ResultActions resultActions = mockMvc.perform(post(BASE_URL)
                .header(HttpHeaders.AUTHORIZATION, TokenService.generateToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBodyAsJson)
                .characterEncoding("utf-8"));
        // then
        resultActions.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("time",
                        is(notNullValue())))
                .andExpect(jsonPath("message",
                        is(message)))
                .andExpect(jsonPath("httpStatus",
                        is(400)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
