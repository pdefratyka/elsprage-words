package com.elsprage.words.integration;

import com.elsprage.words.AbstractIT;
import io.restassured.builder.RequestSpecBuilder;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class HealthControllerTestIT extends AbstractIT {

    @Test
    void healthCheckShouldReturn200() {
        requestSpecification = new RequestSpecBuilder()
                .setPort(localPort)
                .build();
        given(requestSpecification)
                .when()
                .get("/actuator/health")
                .then()
                .statusCode(200);
    }
}
