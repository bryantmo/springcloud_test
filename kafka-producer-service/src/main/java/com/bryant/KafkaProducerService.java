package com.bryant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaProducerService {
    public static void main(String[] args) {
        SpringApplication.run(KafkaProducerService.class, args);
    }
}