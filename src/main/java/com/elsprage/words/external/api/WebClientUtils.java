package com.elsprage.words.external.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.core.ResolvableType;
import org.springframework.core.codec.Hints;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.util.MimeType;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.function.Consumer;

public final class WebClientUtils {

    private static final String HEADER_API = "O-API";

    private WebClientUtils() {

    }

    public static WebClient.Builder createWebClientBuilder(WebClientProperties properties, Logger log,
                                                           ClientHttpConnector clientHttpConnector,
                                                           ObjectMapper objectMapper) {
        final Consumer<ClientCodecConfigurer> consumer = clientCodecConfigurer -> {
            clientCodecConfigurer.defaultCodecs().jackson2JsonDecoder(getJsonDecoder(log, objectMapper));
            clientCodecConfigurer.defaultCodecs().jackson2JsonEncoder(getJsonEncoder(log, objectMapper));
        };

        final ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder().codecs(consumer).build();
        StringBuilder sb = new StringBuilder();
        sb.append(properties.getUrl());
        String contextPath = sb.toString();
        return WebClient.builder()
                .clientConnector(clientHttpConnector)
                .exchangeStrategies(exchangeStrategies)
                .filter(createLogRequestFilter(log))
                .filter(createLogResponseFilter(log))
                .baseUrl(contextPath)
                .defaultHeader(HEADER_API, properties.getApiName());
    }

    protected static Jackson2JsonEncoder getJsonEncoder(Logger log, ObjectMapper objectMapper) {
        return new Jackson2JsonEncoder(objectMapper) {
            @Override
            public DataBuffer encodeValue(Object value, DataBufferFactory bufferFactory, ResolvableType valueType,
                                          MimeType mimeType, Map<String, Object> hints) {
                if (shouldLog(hints)) {
                    DataBuffer dataBuffer = super.encodeValue(value, bufferFactory, valueType, mimeType,
                            hintDisableObjectToStringLogging(hints));
                    log.info("Request RAW JSON: {}", dataBuffer.toString(Charset.defaultCharset()));
                    return dataBuffer;
                } else {
                    return super.encodeValue(value, bufferFactory, valueType, mimeType, hints);
                }
            }
        };
    }

    protected static Jackson2JsonDecoder getJsonDecoder(Logger log, ObjectMapper objectMapper) {
        return new Jackson2JsonDecoder(objectMapper) {
            @Override
            public Object decode(DataBuffer dataBuffer, ResolvableType targetType, MimeType mimeType, Map<String,
                    Object> hints) {
                if (shouldLog(hints)) {
                    log.info("Request RAW JSON: {}", dataBuffer.toString(Charset.defaultCharset()));
                }
                return super.decode(dataBuffer, targetType, mimeType, hintDisableObjectToStringLogging(hints));
            }
        };
    }

    private static Map<String, Object> hintDisableObjectToStringLogging(Map<String, Object> hints) {
        return Hints.merge(hints, Hints.SUPPRESS_LOGGING_HINT, true);
    }

    private static boolean shouldLog(Map<String, Object> hints) {
        return (hints.get(Hints.SUPPRESS_LOGGING_HINT) == null || hints.get(Hints.SUPPRESS_LOGGING_HINT) == Boolean.FALSE);
    }

    protected static ExchangeFilterFunction createLogRequestFilter(Logger log) {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            StringBuilder sb = new StringBuilder();
            sb.append("Request: ");
            sb.append(clientRequest.method());
            sb.append(" url: ");
            sb.append(clientRequest.url());
            sb.append(" headers:");
            appendHeaders(sb, clientRequest.headers());
            log.info(sb.toString());
            return Mono.just(clientRequest);
        });
    }

    protected static ExchangeFilterFunction createLogResponseFilter(Logger log) {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            StringBuilder sb = new StringBuilder();
            sb.append("Response status: ");
            sb.append(clientResponse.rawStatusCode());
            sb.append(" headers:");
            appendHeaders(sb, clientResponse.headers().asHttpHeaders());
            log.info(sb.toString());
            return Mono.just(clientResponse);
        });
    }

    private static void appendHeaders(StringBuilder sb, HttpHeaders httpHeaders) {
        httpHeaders.forEach((name, values) -> values.forEach(value -> {
            sb.append(" ");
            sb.append(name);
            sb.append("=");
            sb.append(value);
        }));
    }
}
