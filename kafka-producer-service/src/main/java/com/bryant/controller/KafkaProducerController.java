package com.bryant.controller;

import com.bryant.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/producer")
public class KafkaProducerController {

    @Autowired
    private KafkaService kafkaService;

    @PostMapping("/create_message")
//    @GetMapping("/create_message")
    public void createMessage(
            @RequestParam("message") String message,
            @RequestParam("key") Integer key) {
        kafkaService.createMessageSyn(message, key);
    }

    @PostMapping("/create_message_asyn")
//    @GetMapping("/create_message_asyn")
    public void createMessageAsyn(
            @RequestParam("message") String message,
            @RequestParam("key") Integer key) {
        kafkaService.createMessageAsyn(message, key);
    }

    @PostMapping("/create_message_batch_asyn")
//    @GetMapping("/create_message_batch_asyn")
    public void batchCreateMessageAsyn(
            @RequestParam("message") String message,
            @RequestParam("key") Integer key) {
        kafkaService.batchCreateMessageAsyn(message, key);
    }

}
