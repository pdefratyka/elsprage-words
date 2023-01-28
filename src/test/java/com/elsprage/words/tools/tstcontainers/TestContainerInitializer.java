package com.elsprage.words.tools.tstcontainers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestContainerInitializer {

    public static void start(ContainerInitializer<?>... containerInitializers) {
        Arrays.stream(containerInitializers).parallel().forEach(ContainerInitializer::startAndGet);
    }
}
