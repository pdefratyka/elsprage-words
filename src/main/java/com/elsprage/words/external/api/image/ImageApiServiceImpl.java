package com.elsprage.words.external.api.image;

import com.elsprage.words.external.api.WebClientUtils;
import com.elsprage.words.model.response.ImageApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;

@Service
@Slf4j
public class ImageApiServiceImpl implements ImageApiService {
    private static final int TIMEOUT = 30000;
    private static final String HEADER_REQUEST_TIMEOUT = "O-Request-Timeout";
    private final WebClient webClient;
    private final String authorizationKey;

    public ImageApiServiceImpl(final ImageApiProperties imageApiProperties, final ClientHttpConnector clientHttpConnector) {
        final ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
        this.webClient = WebClientUtils.createWebClientBuilder(imageApiProperties, log, clientHttpConnector,
                objectMapper).build();
        authorizationKey = imageApiProperties.getAuthorization();
    }

    @Override
    public ImageApiResponse getImage(String keyword) {
        final WebClient.RequestHeadersSpec<?> requestHeadersSpec = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search")
                        .queryParam("query", keyword)
                        .build())
                .header("Authorization", authorizationKey)
                .accept(MediaType.APPLICATION_JSON);
        try {
            return requestHeadersSpec.retrieve().bodyToMono(new ParameterizedTypeReference<ImageApiResponse>() {
            }).timeout(Duration.ofMillis(TIMEOUT)).block();
        } catch (WebClientResponseException.NotFound e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
