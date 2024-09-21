package com.bryant.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kafka.consumer")
@Data
public class KafkaConsumerProperties {
    private String clientId;
    private String brokerList;
    private String groupId;
    private String topic;
}
