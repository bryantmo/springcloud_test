package com.bryant.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

/**
 * 消息消费者
 */
@Component
@EnableBinding(Sink.class)
public class MessageConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageConsumer.class);

//    @EventListener
    @StreamListener(Sink.INPUT)
    public void receiveMessage(Object message) {
        String content = message.toString();
        LOGGER.info("receive message: " + content);
    }
}
