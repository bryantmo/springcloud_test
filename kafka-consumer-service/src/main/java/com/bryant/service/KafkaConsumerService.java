package com.bryant.service;

import com.bryant.config.KafkaConsumerProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class KafkaConsumerService {

    @Autowired
    private KafkaConsumer kafkaConsumer;

    @Autowired
    private KafkaConsumerProperties kafkaConsumerProperties;

    @PostConstruct
    public void consume() throws Exception {
        log.info("KafkaConsumer start");
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            kafkaConsumer.subscribe(Arrays.asList(kafkaConsumerProperties.getTopic()));

            while (true)  {
                try {
                    ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(1000));
                    for (ConsumerRecord<String, String> record :records.records(kafkaConsumerProperties.getTopic())) {
                        log.info("topic = " + record.topic()
                                + ", partition = " + record.partition()
                                + ", offset = " + record.offset());



                    }
                } catch (Exception e) {
                    log.error("KafkaConsumer error", e);
                }
            }
        });
    }
}
