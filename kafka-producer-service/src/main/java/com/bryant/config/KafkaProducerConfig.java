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

        // 添加单个拦截器
//        props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, CustomProducerInteceptor.class.getName());

        // 添加多个拦截器
        props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, CustomProducerInteceptor.class.getName() + "," + CustomProducerLogInteceptor.class.getName());

        return props;
    }


}
