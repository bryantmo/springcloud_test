package com.bryant.controller;

import com.bryant.service.KafkaConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaConsumerController {

    @Autowired
    private KafkaConsumerService kafkaConsumerService;

    @GetMapping("/consume")
    public void consume() throws Exception {
        kafkaConsumerService.consume();
    }

}

