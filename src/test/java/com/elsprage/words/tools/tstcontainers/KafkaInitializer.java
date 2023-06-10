package com.elsprage.words.tools.tstcontainers;

import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

public class KafkaInitializer implements ContainerInitializer<KafkaContainer> {

    private static KafkaContainer container;

    private static KafkaContainer getContainer() {
        if (container == null) {
            container = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3"))
                    .withReuse(true);
        }
        return container;
    }

    @Override
    public KafkaContainer startAndGet() {
        getContainer().start();
        System.setProperty("spring.kafka.bootstrap-servers", container.getBootstrapServers());
        System.setProperty("app.kafka.bootstrap-servers", container.getBootstrapServers());
        return getContainer();
    }
}