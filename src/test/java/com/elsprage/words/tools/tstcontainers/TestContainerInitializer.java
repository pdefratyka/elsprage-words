package com.elsprage.words.tools.tstcontainers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;

@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestContainerInitializer {

    public static void start(ContainerInitializer<?>... containerInitializers) {
        Arrays.stream(containerInitializers).parallel().forEach(ContainerInitializer::startAndGet);
    }
}
