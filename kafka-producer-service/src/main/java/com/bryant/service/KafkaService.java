package com.bryant.service;

import com.bryant.config.KafkaProducerProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
@Slf4j
public class KafkaService {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private KafkaProducerProperties kafkaProducerProperties;

    /**
     * 同步发送kafka
     * @param message
     * @param key
     */
    public void createMessageSyn(String message, Integer key) {
        ProducerRecord<Object, String> record =
                new ProducerRecord<>(kafkaProducerProperties.getTopic(), getPartition(key), message);
        try {
            Future<RecordMetadata> sendResult = kafkaProducer.send(record);
            RecordMetadata recordMetadata = sendResult.get();
            log.info("createMessageSyn: RecordMetadata = {}", recordMetadata.toString());
        } catch (InterruptedException | ExecutionException e) {
            log.error("createMessageSyn error, ", e);
        }
    }

    /**
     * 异步发送kafka
     * @param message
     * @param key
     */
    public void createMessageAsyn(String message, Integer key) {
        ProducerRecord<Object, String> record =
                new ProducerRecord<>(kafkaProducerProperties.getTopic(), getPartition(key).toString(), message);
        kafkaProducer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                // metadata 和 exception
                if (exception != null) {
                    log.error("createMessageSyn error: ", exception);
                } else {
                    log.info("createMessageSyn: RecordMetadata = {}", metadata.toString());
                }
            }
        });
    }

    public void batchCreateMessageAsyn(String message, Integer key) {
        int i = 0;
        while (i < 100) {
            ProducerRecord<Object, String> record =
                    new ProducerRecord<>(kafkaProducerProperties.getTopic(), getPartition(key), message);
            kafkaProducer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    // metadata 和 exception
                    if (exception != null) {
                        log.error("batchCreateMessageAsyn error: ", exception);
                    } else {
                        log.info("batchCreateMessageAsyn: RecordMetadata = {}", metadata.toString());
                    }
                }
            });
        }
    }

    private Integer getPartition(Integer key) {
        return key%100;
    }
}
