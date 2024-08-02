package com.bryant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息发送者
 */
@RestController
@EnableBinding(Source.class)
@RequestMapping("/stream")
public class MessageController {

    @Autowired
    private Source source;

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/sendMessage")
    public String sendMessage(@RequestParam("message") String message) {
        MessageBuilder<String> messageBuilder = MessageBuilder.withPayload("message");
        source.output().send(messageBuilder.build());
        return "message send : " + message;
    }
}
