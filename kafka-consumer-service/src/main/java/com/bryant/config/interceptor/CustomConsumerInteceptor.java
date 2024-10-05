package com.bryant.config.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.Map;

@Slf4j
public class CustomConsumerInteceptor implements ConsumerInterceptor {
    @Override
    public ConsumerRecords onConsume(ConsumerRecords records) {
        log.info("CustomConsumerInteceptor, records.count = {}", records.count());
        return null;
    }

    @Override
    public void close() {

    }

    @Override
    public void onCommit(Map offsets) {
        log.info("CustomConsumerInteceptor, commit record offset = {}", offsets.entrySet());
    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
