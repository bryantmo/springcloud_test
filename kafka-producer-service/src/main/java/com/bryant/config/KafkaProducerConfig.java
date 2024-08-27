package com.bryant.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KafkaProducerConfig {

    @Autowired
    private KafkaProducerProperties kafkaProducerProperties;

    @Bean
    public KafkaProducer kafkaProducer() {
        Properties properties = initConfig();
        KafkaProducer<Object, Object> producer = new KafkaProducer<>(properties);
        return producer;
    }

    private Properties initConfig() {
        Properties props = new Properties();
        props.put("bootstrap.servers", kafkaProducerProperties.getBrokerList());
        props.put("kafka.producer.topic", kafkaProducerProperties.getTopic());
        props.put("kafka.producer.key-serializer", kafkaProducerProperties.getKeySerializer());
        props.put("kafka.producer.value-serializer", kafkaProducerProperties.getValueSerializer());
        return props;
    }


}
