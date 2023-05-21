package com.elsprage.words.tools.tstcontainers;

import lombok.extern.log4j.Log4j2;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@Log4j2
public class PostgresInitializer implements ContainerInitializer<PostgreSQLContainer> {

    private static PostgreSQLContainer<?> container;

    private static PostgreSQLContainer<?> getContainer() {
        if (container == null) {
            container = new PostgreSQLContainer<>(DockerImageName.parse("postgres:14"))
                    .withUsername("elsprage_users")
                    .withPassword("test_password")
                    .withDatabaseName("test")
                    .withReuse(true);
        }
        return container;
    }

    @Override
    public PostgreSQLContainer startAndGet() {
        getContainer().start();
        System.setProperty("spring.datasource.url", container.getJdbcUrl());
        System.setProperty("spring.datasource.username", container.getUsername());
        System.setProperty("spring.datasource.password", container.getPassword());
        return getContainer();
    }
}
