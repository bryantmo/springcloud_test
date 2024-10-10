package com.bryant.service;

import com.bryant.config.KafkaConsumerProperties;
import com.bryant.config.interceptor.CustomConsumerInteceptor;
import com.bryant.util.DateUtil;
import com.bryant.util.SpringContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
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
//        executorService.execute(new Task1(kafkaConsumerProperties, DEFAULT_PARTITION));

        executorService.execute(new Task2(kafkaConsumerProperties, DEFAULT_PARTITION));
    }

}

@Slf4j
class Task2 implements Runnable {

    private KafkaConsumerProperties kafkaConsumerProperties;
    private String defaultPartition;

    public Task2(KafkaConsumerProperties kafkaConsumerProperties, String defaultPartition) {
        this.defaultPartition = defaultPartition;
        this.kafkaConsumerProperties = kafkaConsumerProperties;
    }

    @SneakyThrows
    @Override
    public void run() {
        KafkaConsumer consumer = new KafkaConsumer<>(initKafkaConsumer());
        consumer.subscribe(Arrays.asList(kafkaConsumerProperties.getTopic()));

        //当poll()方法中的参数为0时,立刻返回，poll()方法内部进行分区分配的逻辑就会来不及实施。
        consumer.poll(Duration.ofMillis(0));
        // 获取消费者获取到的分区信息
        Set<TopicPartition> assignment =  consumer.assignment();

        // 等待分区分配完成，往往是服务启动时，会进行消费者分区分配（ assignment.size 不为 0，则说明已经成功分配到了分区）
        while(assignment.size() == 0) {
            consumer.poll(Duration.ofMillis(1000));
            // 拿到分配到的分区TP，格式是{topic=xxx, partition=xxx}
            assignment = consumer.assignment();
        }

        for (TopicPartition tp : assignment) {
            // 设置从分配到的分区TP，指定从第4条开始消费，最终一次拿到了超过500条的已消费数据。
            consumer.seek(tp, 4);
        }

        // 设置从末尾开始消费
        // consumer.endOffsets: 来获取指定分区的末尾的消息位置
//        Map<TopicPartition, Long> endOffsets = consumer.endOffsets(assignment);
//        for (TopicPartition tp : assignment) {
//            consumer.seek(tp, endOffsets.get(tp));
//        }

        // kafka-api-从开头或结尾消费
//        consumer.seekToBeginning(assignment);
//        consumer.seekToEnd(assignment);

//        for (TopicPartition tp : assignment) {
//            // 指定时间戳消费
//            Map<TopicPartition, OffsetAndTimestamp>  offsets =
//                    consumer.offsetsForTimes(Collections.singletonMap(tp, DateUtil.getToday().getTime()));
//            OffsetAndTimestamp offsetAndTimestamp = offsets.get(tp);
//            consumer.seek(tp, offsetAndTimestamp.offset());
//        }


        // 循环拉数据
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(2000));

            if (records == null || records.isEmpty()) {
                Thread.sleep(1000);
                continue;
            }
            for (ConsumerRecord<String, String> record : records.records(kafkaConsumerProperties.getTopic())) {
                log.info(
                        "applicationName = " + SpringContext.getEnvProperty("spring.application.name")
                                + ", topic = " + record.topic()
                                + ", partition = " + record.partition()
                                + ", offset = " + record.offset());

                log.info("message = {}", record.value());
            }
            consumer.commitSync();
        }
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

        // 消费者拦截器
        props.put(ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG, CustomConsumerInteceptor.class.getName());

        return props;
    }
}

/**
 * 测试以下kafka-consumer功能点
 * 1、同步提交
 *   1.1、精确提交
 *   1.2、分区提交
 *   1.3、自动提交
 * 2、异步提交
 *
 * 以及可能导致的重复提交情况
 */
@Slf4j
class Task1 implements Runnable {

    private KafkaConsumerProperties kafkaConsumerProperties;
    private String defaultPartition;

    public Task1(KafkaConsumerProperties kafkaConsumerProperties, String defaultPartition) {
        this.kafkaConsumerProperties = kafkaConsumerProperties;
        this.defaultPartition = defaultPartition;
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

    @Override
    public void run() {
        KafkaConsumer kafkaConsumer = new KafkaConsumer(initKafkaConsumer());
        kafkaConsumer.subscribe(Arrays.asList(kafkaConsumerProperties.getTopic()));
        while (true) {
            try {
                TopicPartition tp = new TopicPartition(kafkaConsumerProperties.getTopic(), Integer.valueOf(defaultPartition));
                ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(1000));

                if (CollectionUtils.isEmpty(records.records(tp))) {
                    Thread.sleep(1000);
                    continue;
                }
                for (ConsumerRecord<String, String> record : records.records(kafkaConsumerProperties.getTopic())) {
                    log.info(
                            "applicationName = " + SpringContext.getEnvProperty("spring.application.name")
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
    }
}