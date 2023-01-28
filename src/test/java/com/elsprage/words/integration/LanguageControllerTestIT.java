package com.elsprage.words.integration;

import com.elsprage.words.AbstractIT;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class LanguageControllerTestIT extends AbstractIT {

    private static final String URL = "/languages";

    @Test
    void shouldGetLanguages() {
        given(requestSpecification)
                .when()
                .get(URL)
                .then()
                .statusCode(200)
                .body("languages[0].id", equalTo(1))
                .body("languages[0].name", equalTo("polish"))
                .body("languages[0].symbol", equalTo("pl"))
                .body("languages[1].id", equalTo(2))
                .body("languages[1].name", equalTo("english"))
                .body("languages[1].symbol", equalTo("en"))
                .body("languages[2].id", equalTo(3))
                .body("languages[2].name", equalTo("german"))
                .body("languages[2].symbol", equalTo("de"));
    }
}
