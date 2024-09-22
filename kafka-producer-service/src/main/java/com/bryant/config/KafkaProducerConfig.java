package com.bryant.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KafkaProducerConfig {

    @Autowired
    private KafkaProducerProperties kafkaProducerProperties;

    @Bean
    public KafkaProducer kafkaProducer() {
        Properties properties = initConfig();
        KafkaProducer<Object, Object> producer = new KafkaProducer<>(properties);
        return producer;
    }

    /**
     * @see {@link org.apache.kafka.clients.producer.ProducerConfig}
     * @return
     */
    private Properties initConfig() {
        Properties props = new Properties();
        props.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaProducerProperties.getClientId());
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProducerProperties.getBrokerList());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaProducerProperties.getKeySerializer());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaProducerProperties.getValueSerializer());

        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomPartitioner.class.getName());

        // Producer等待更多消息加入ProducerBatch时间，默认0
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1000);

        // Producer等待请求响应的最长时间，默认30000ms
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 1000);

        // acks参数：指定多少个分区收到这条消息，才算消息写入成功,消息可靠性
        props.put(ProducerConfig.ACKS_CONFIG, "1");

        // 设置生产者阻塞时间
        props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 3000);

        // 添加单个拦截器
//        props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, CustomProducerInteceptor.class.getName());

        // 添加多个拦截器
        props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, CustomProducerInteceptor.class.getName() + "," + CustomProducerLogInteceptor.class.getName());

        return props;
    }


}
