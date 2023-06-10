package com.elsprage.words;

import com.elsprage.words.persistance.repository.WordRepository;
import com.elsprage.words.tools.tstcontainers.KafkaInitializer;
import com.elsprage.words.tools.tstcontainers.MockServerInitializer;
import com.elsprage.words.tools.tstcontainers.PostgresInitializer;
import com.elsprage.words.tools.tstcontainers.TestContainerInitializer;
import com.elsprage.words.tools.utils.TokenService;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

@ActiveProfiles("it-test")
@SpringBootTest(classes = WordsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractIT {

    @LocalServerPort
    protected int localPort;

    @Autowired
    private WordRepository wordRepository;

    protected RequestSpecification requestSpecification;
    // TODO we can remove mock server initializer, but it is good to show example of configuration
    static {
        KafkaInitializer kafkaInitializer = new KafkaInitializer();
        TestContainerInitializer.start(
                new PostgresInitializer(),
                new KafkaInitializer(),
                new MockServerInitializer());
    }

    @BeforeEach
    public void setUpAbstractIntegrationTest() throws IOException {
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
