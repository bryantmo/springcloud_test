package com.bryant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaConsumerServer {
    public static void main(String[] args) {
        SpringApplication.run(KafkaConsumerServer.class, args);
    }
}