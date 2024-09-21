package com.bryant.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 拦截器主要用来在发消息之前，做一些准备工作：
 * 1. 按某个规则过滤不符合要求的消息
 * 2. 修改消息内容
 * 3. 发送回调逻辑前做一些定制需求，比如统计类工作等等
 */
@Slf4j
public class CustomProducerInteceptor implements ProducerInterceptor {

    private static final String PREFIX = "test-";
    private static final AtomicInteger successCount = new AtomicInteger(0);
    private static final AtomicInteger failCount = new AtomicInteger(0);

    @Override
    public ProducerRecord onSend(ProducerRecord record) {
        String newMessage = PREFIX + (String) record.value();
        log.info("CustomProducerInteceptor onSend message = {}", record);
        return new ProducerRecord(record.topic(), record.partition(), record.timestamp(),
                                    record.key(), newMessage, record.headers());
    }

    /**
     * 生产者在Ack前，调用onAcknowledgement方法
     * @param metadata The metadata for the record that was sent (i.e. the partition and offset).
     *                 If an error occurred, metadata will contain only valid topic and maybe
     *                 partition. If partition is not given in ProducerRecord and an error occurs
     *                 before partition gets assigned, then partition will be set to RecordMetadata.NO_PARTITION.
     *                 The metadata may be null if the client passed null record to
     *                 {@link org.apache.kafka.clients.producer.KafkaProducer#send(ProducerRecord)}.
     * @param exception The exception thrown during processing of this record. Null if no error occurred.
     */
    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        // metadata 和 exception
        if (exception != null) {
            failCount.incrementAndGet();
            log.error("CustomProducerInteceptor onAcknowledgement error: ", exception);
        } else {
            successCount.incrementAndGet();
            log.info("CustomProducerInteceptor onAcknowledgement metadata = {}", metadata);
        }
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
