package com.elsprage.words.web.controller;

import com.elsprage.words.AbstractControllerTest;
import com.elsprage.words.common.constants.ExceptionConstants;
import com.elsprage.words.model.dto.LanguageDTO;
import com.elsprage.words.service.LanguageService;
import com.elsprage.words.service.impl.LanguageServiceImpl;
import com.elsprage.words.web.exception.ControllerAdvice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class LanguageControllerTest extends AbstractControllerTest {

    private static final String BASE_URL = "/languages";

    private MockMvc mockMvc;

    private LanguageService languageService;

    @BeforeEach
    void setup() {
        languageService = Mockito.mock(LanguageServiceImpl.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new LanguageController(languageService))
                .setControllerAdvice(new ControllerAdvice())
                .build();
    }

    @Test
    void shouldReturnLanguagesResponse() throws Exception {
        LanguageDTO languageDTO = LanguageDTO.builder()
                .id(1L)
                .name("English")
                .symbol("EN")
                .build();
        when(languageService.getLanguages()).thenReturn(List.of(languageDTO));
        // when
        ResultActions resultActions = mockMvc.perform(get(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"));
        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("languages[0].name",
                        is(languageDTO.getName())))
                .andExpect(jsonPath("languages[0].symbol",
                        is(languageDTO.getSymbol())))
                .andExpect(jsonPath("languages[0].id",
                        is(languageDTO.getId().intValue())));
    }

    @Test
    void shouldReturnInternalServerError() throws Exception {
        when(languageService.getLanguages()).thenThrow(new RuntimeException());
        // when
        ResultActions resultActions = mockMvc.perform(get(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"));
        // then
        resultActions.andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("message",
                        is(ExceptionConstants.INTERNAL_SERVER_ERROR)))
                .andExpect(jsonPath("httpStatus",
                        is(500)))
                .andExpect(jsonPath("time", is(notNullValue())));
    }
}
