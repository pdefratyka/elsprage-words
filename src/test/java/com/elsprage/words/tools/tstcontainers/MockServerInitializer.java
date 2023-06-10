package com.elsprage.words.tools.tstcontainers;

import lombok.extern.log4j.Log4j2;
import org.mockserver.client.MockServerClient;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.utility.DockerImageName;

@Log4j2
public class MockServerInitializer implements ContainerInitializer<MockServerContainer> {

    private static final DockerImageName MOCKSERVER_IMAGE = DockerImageName
            .parse("mockserver/mockserver")
            .withTag("mockserver-" + MockServerClient.class.getPackage().getImplementationVersion());

    private static MockServerContainer container;

    private static MockServerContainer getContainer() {
        if (container == null) {
            container = new MockServerContainer(MOCKSERVER_IMAGE)
                    .withReuse(true);
        }
        return container;
    }

    @Override
    public MockServerContainer startAndGet() {
        getContainer().start();
        log.info("MockServer host: {}, port: {}", container.getHost(), container.getServerPort());
        System.setProperty("app.mock.server.host", String.valueOf(container.getHost()));
        System.setProperty("app.mock.server.port", String.valueOf(container.getServerPort()));
        return getContainer();
    }
}
