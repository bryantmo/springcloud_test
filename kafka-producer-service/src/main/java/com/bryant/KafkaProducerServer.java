package com.bryant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaProducerServer {
    public static void main(String[] args) {
        SpringApplication.run(KafkaProducerServer.class, args);
    }
}