package com.bryant.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kafka.producer")
public class KafkaProducerProperties {

    private String clientId;
    private String brokerList;
    private String topic;
    private String keySerializer;
    private String valueSerializer;

    private String multiPartitionTopic;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getBrokerList() {
        return brokerList;
    }

    public void setBrokerList(String brokerList) {
        this.brokerList = brokerList;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getKeySerializer() {
        return keySerializer;
    }

    public void setKeySerializer(String keySerializer) {
        this.keySerializer = keySerializer;
    }

    public String getValueSerializer() {
        return valueSerializer;
    }

    public void setValueSerializer(String valueSerializer) {
        this.valueSerializer = valueSerializer;
    }

    public String getMultiPartitionTopic() {
        return multiPartitionTopic;
    }

    public void setMultiPartitionTopic(String multiPartitionTopic) {
        this.multiPartitionTopic = multiPartitionTopic;
    }
}
