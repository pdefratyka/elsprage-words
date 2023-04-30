package com.elsprage.words;

import com.elsprage.words.external.api.image.ImageApiService;
import com.elsprage.words.persistance.repository.WordRepository;
import com.elsprage.words.tools.tstcontainers.PostgresInitializer;
import com.elsprage.words.tools.tstcontainers.TestContainerInitializer;
import com.elsprage.words.tools.utils.TokenService;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("it-test")
@SpringBootTest(classes = WordsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractIT {

    @LocalServerPort
    protected int localPort;

    @MockBean
    private ImageApiService imageApiService;

    @Autowired
    private WordRepository wordRepository;

    protected RequestSpecification requestSpecification;

    static {
        TestContainerInitializer.start(
                new PostgresInitializer());
    }

    private void prepareResponses() throws IOException {
        when(imageApiService.getImage(any())).thenReturn(null);
    }

    @BeforeEach
    public void setUpAbstractIntegrationTest() throws IOException {
        prepareResponses();
        requestSpecification = new RequestSpecBuilder()
                .setPort(localPort)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + TokenService.generateToken())
                .build();
    }

    @AfterEach
    public void afterEach() {
        wordRepository.deleteAll();
    }
}
