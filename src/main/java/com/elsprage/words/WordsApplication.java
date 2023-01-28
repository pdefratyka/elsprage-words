package com.elsprage.words;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
//@EnableDiscoveryClient
@Slf4j
public class WordsApplication {

    public static void main(String... args) {
        final SpringApplicationBuilder builder = new SpringApplicationBuilder(WordsApplication.class);
        final ConfigurableApplicationContext context = builder.run(args);
    }

    static String getStartupInfo(final ConfigurableApplicationContext context) {
        final Environment env = context.getEnvironment();
        final String port = env.getProperty("server.port");
        final List<String> profile = Arrays.asList(env.getActiveProfiles());
        return "Started Spring Boot application: WordsApplication on port: " + port + " with profile " + profile;
    }
}