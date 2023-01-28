package com.elsprage.words.integration;

import com.elsprage.words.AbstractIT;
import com.elsprage.words.model.dto.WordDTO;
import com.elsprage.words.persistance.entity.Word;
import com.elsprage.words.persistance.repository.WordRepository;
import com.elsprage.words.service.ImageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class WordsControllerTestIT extends AbstractIT {

    private static final String URL = "/words";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private ImageService imageService;

    @Autowired
    private WordRepository wordRepository;

    @Test
    void shouldSaveWord() throws IOException {
        // given
        final WordDTO requestBody = getWordDTORequest();
        requestSpecification = requestSpecification
                .body(objectMapper.writeValueAsString(requestBody));
        when(imageService.getImageFromUrl(any())).thenReturn(null);
        // when && then
        given(requestSpecification)
                .when()
                .post(URL)
                .then()
                .statusCode(200)
                .body("word.value", equalTo(requestBody.getValue()))
                .body("word.translation", equalTo(requestBody.getTranslation()))
                .body("word.userId", equalTo(requestBody.getUserId().intValue()))
                .body("word.sound", equalTo(requestBody.getSound()))
                .body("word.image", equalTo(requestBody.getImage()))
                .body("word.example", equalTo(requestBody.getExample()))
                .body("word.translationLanguageId", equalTo(requestBody.getTranslationLanguageId().intValue()))
                .body("word.valueLanguageId", equalTo(requestBody.getValueLanguageId().intValue()));

        Word resultWord = wordRepository.findById(1L).get();

        assertEquals(1, wordRepository.findAll().size());
        assertEquals(requestBody.getValue(), resultWord.getValue());
        assertEquals(requestBody.getTranslation(), resultWord.getTranslation());
        assertEquals(requestBody.getSound(), resultWord.getSound());
        assertEquals(requestBody.getExample(), resultWord.getExample());
        assertEquals(requestBody.getImage(), resultWord.getImage());
        assertEquals(requestBody.getTranslationLanguageId(), resultWord.getTranslationLanguageId());
        assertEquals(requestBody.getValueLanguageId(), resultWord.getValueLanguageId());
        assertEquals(requestBody.getUserId(), resultWord.getUserId());
    }

    private WordDTO getWordDTORequest() {
        return WordDTO.builder()
                .value("pies")
                .translation("dog")
                .valueLanguageId(1L)
                .translationLanguageId(2L)
                .sound("sound")
                .image("http://www.someImage.pl/dog.png")
                .example("Dog is a pet")
                .userId(1L)
                .build();
    }
}
