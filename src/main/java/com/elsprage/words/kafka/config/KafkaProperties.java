package com.elsprage.words.kafka.config;

import com.elsprage.words.common.constants.Constants;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Configuration
@Validated
@ConfigurationProperties(prefix = KafkaProperties.PREFIX)
public class KafkaProperties {

    public static final String PREFIX = Constants.APP_PREFIX + ".kafka";

    @NotNull
    private String schemaRegistryUrl;

    @NotNull
    private String bootstrapServers;

    @NotNull
    private String groupId;

    @NotNull
    private String clientId;
}
