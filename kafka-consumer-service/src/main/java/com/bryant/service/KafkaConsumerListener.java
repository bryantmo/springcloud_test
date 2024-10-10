package com.bryant.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumerListener {

    @KafkaListener(topics = "topic-demo", groupId = "test-group2")
    public void listen(ConsumerRecord<String, String> record) {
        log.info("listener consume record : ", record);
    }

}
