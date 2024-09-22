package com.bryant.service;

import com.bryant.config.KafkaConsumerProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class KafkaConsumerService {
//
//    @Autowired
//    private KafkaConsumer kafkaConsumer;

    @Autowired
    private KafkaConsumerProperties kafkaConsumerProperties;

    @PostConstruct
    public void consume() throws Exception {
        log.info("KafkaConsumer start");
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {

            KafkaConsumer kafkaConsumer = new KafkaConsumer(initKafkaConsumer());
            kafkaConsumer.subscribe(Arrays.asList(kafkaConsumerProperties.getTopic()));

            while (true)  {
                try {
                    ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(1000));
                    for (ConsumerRecord<String, String> record :records.records(kafkaConsumerProperties.getTopic())) {
                        log.info("topic = " + record.topic()
                                + ", partition = " + record.partition()
                                + ", offset = " + record.offset());
                    }
                    kafkaConsumer.commitAsync();
                } catch (Exception e) {
                    log.error("KafkaConsumer error", e);
                }
            }
        });
    }

    private Properties initKafkaConsumer() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConsumerProperties.getBrokerList());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConsumerProperties.getGroupId());
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, kafkaConsumerProperties.getClientId());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // 设置手动提交offset
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");

        return props;
    }
}
