package com.bryant.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@Slf4j
public class KafkaConsumerConfig {

    @Autowired
    private KafkaConsumerProperties kafkaConsumerProperties;

    @Bean
    public KafkaConsumer kafkaConsumer() {
        log.info("KafkaConsumer start");
        KafkaConsumer kafkaConsumer = new KafkaConsumer(initKafkaConsumer());
        kafkaConsumer.subscribe(Arrays.asList(kafkaConsumerProperties.getTopic()));
        return kafkaConsumer;
    }

    private Properties initKafkaConsumer() {
        Properties props = new Properties();
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConsumerProperties.getBrokerList());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConsumerProperties.getGroupId());
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, kafkaConsumerProperties.getClientId());
        return props;
    }

}
