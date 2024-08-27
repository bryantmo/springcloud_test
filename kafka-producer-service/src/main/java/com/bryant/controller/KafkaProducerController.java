package com.bryant.controller;

import com.bryant.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaProducerController {

    @Autowired
    private KafkaService kafkaService;

    @PostMapping("/create_message")
    public void createMessage(
            @RequestParam("message") String message,
            @RequestParam("key") Integer key) {
        kafkaService.createMessage(message, key);
    }

}
