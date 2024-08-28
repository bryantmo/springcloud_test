package com.bryant.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kafka.producer")
@Data
public class KafkaProducerProperties {

    private String clientId;
    private String brokerList;
    private String topic;
    private String keySerializer;
    private String valueSerializer;

}
