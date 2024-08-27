package com.bryant.service;

import com.bryant.config.KafkaProducerProperties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private KafkaProducerProperties kafkaProducerProperties;

    public void createMessage(String message, Integer key) {
        ProducerRecord<Object, String> record =
                new ProducerRecord<>(kafkaProducerProperties.getTopic(), getPartition(key), message);
        kafkaProducer.send(record);
    }

    private Integer getPartition(Integer key) {
        return key%100;
    }
}
