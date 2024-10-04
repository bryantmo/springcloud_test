package com.bryant.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.bryant.config.KafkaConsumerProperties;
import com.bryant.model.Users;
import com.bryant.util.JsonUtil;
import com.bryant.util.SpringContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class KafkaConsumerService {

    private static final String DEFAULT_PARTITION = "0";

    @Autowired
    private KafkaConsumerProperties kafkaConsumerProperties;

    @Autowired
    private SpringContext springContext;

    @PostConstruct
    public void consume() throws Exception {
        log.info("KafkaConsumer start");
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {

            KafkaConsumer kafkaConsumer = new KafkaConsumer(initKafkaConsumer());
            kafkaConsumer.subscribe(Arrays.asList(kafkaConsumerProperties.getTopic()));
            while (true) {
                try {
                    TopicPartition tp = new TopicPartition(kafkaConsumerProperties.getTopic(), Integer.valueOf(DEFAULT_PARTITION));
                    ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(1000));

                    if (CollectionUtils.isEmpty(records.records(tp))) {
                        Thread.sleep(1000);
                        continue;
                    }

                    for (ConsumerRecord<String, String> record : records.records(kafkaConsumerProperties.getTopic())) {
                        log.info(
                                "applicationName = " + springContext.getEnvProperty("spring.application.name")
                                + ", topic = " + record.topic()
                                + ", partition = " + record.partition()
                                + ", offset = " + record.offset());

                        log.info("message = {}", record.value());
//                        Users user = JSON.parseObject(record.value(), Users.class);
//                        log.info("user = {}", user);

                        // 更精确的分区offset提交
                        kafkaConsumer.commitSync(Collections.singletonMap(tp, new OffsetAndMetadata(record.offset() + 1)));
                    }

                    // 取某个topic&分区的数据
                    List<ConsumerRecord<String, String>> partitionRecords =  records.records(tp);
                    long lastCommitOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
                    log.info("lastCommitOffset is {}", lastCommitOffset);

                    // 同步提交位移
//                    kafkaConsumer.commitSync();

                    // 分区维度的提交位移
//                    kafkaConsumer.commitSync(Collections.singletonMap(tp, new OffsetAndMetadata(lastCommitOffset + 1)));

                    try {
                        kafkaConsumer.commitAsync();
                    } finally {
                        try {
                            kafkaConsumer.commitAsync();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    OffsetAndMetadata offsetAndMetadata = kafkaConsumer.committed(tp);
                    log.info("offsetAndMetadata is {}", offsetAndMetadata.offset());

                    long position = kafkaConsumer.position(tp);
                    log.info("the offset of the next record is {}", position);

//                    // 异步提交位移
//                    kafkaConsumer.commitAsync();

                    Thread.sleep(100);
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
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");

        return props;
    }
}
