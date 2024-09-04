package com.bryant.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@ConditionalOnProperty(prefix = "kafka.consumer", havingValue = "enable", matchIfMissing = true)
@Data
public class KafkaConsumerProperties {

    private String clientId;
    private String brokerList;
    private String groupId;
}
