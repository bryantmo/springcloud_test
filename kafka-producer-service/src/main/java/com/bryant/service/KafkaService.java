package com.bryant.service;

import com.bryant.config.KafkaProducerProperties;
import com.bryant.model.Users;
import com.bryant.util.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
@Slf4j
public class KafkaService {

    private static final String DEFAULT_PARTITION = "0";
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
                new ProducerRecord<>(kafkaProducerProperties.getTopic(), getPartition(key).toString(), message);
        try {
            Future<RecordMetadata> sendResult = kafkaProducer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e != null) {
                        log.error("send message error, ", e);
                    } else {
                        log.info("send message finish, metadata = {}", JsonUtil.toJson2(recordMetadata));
                    }
                }
            });
            RecordMetadata recordMetadata = sendResult.get();
            log.info("createMessageSyn: RecordMetadata = {}", JsonUtil.toJson2(recordMetadata));
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
                    log.info("createMessageSyn: RecordMetadata = {}", JsonUtil.toJson2(metadata));
                }
            }
        });
    }

    public void batchCreateMessageAsyn(String message, Integer key) {
        int i = 0;
        while (i++ < 100) {
            ProducerRecord<Object, String> record =
                    new ProducerRecord<>(kafkaProducerProperties.getTopic(), getPartition(key).toString(), message);
            kafkaProducer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    // metadata 和 exception
                    if (exception != null) {
                        log.error("batchCreateMessageAsyn error: ", exception);
                    } else {
                        log.info("batchCreateMessageAsyn: RecordMetadata = {}", JsonUtil.toJson2(metadata));
                    }
                }
            });
        }
    }

    private Integer getPartition(Integer key) {
        return key%100;
    }

    public void batchSendMessages(List<Users> users) {
        if (CollectionUtils.isEmpty(users)) {
            return;
        }

        for (Users user : users) {
            ProducerRecord record = new ProducerRecord<>(kafkaProducerProperties.getTopic(), DEFAULT_PARTITION, JsonUtil.toJson2(user));
            kafkaProducer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    // metadata 和 exception
                    if (exception != null) {
                        log.error("batchSendMessages error: ", exception);
                    } else {
                        log.info("batchSendMessages: topic = {}, partition = {}, offset = {}",
                                metadata.topic(), metadata.partition(), metadata.offset());
                    }
                }
            });
        }
    }
}
