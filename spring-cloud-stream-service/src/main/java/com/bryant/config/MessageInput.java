package com.bryant.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MessageInput {

    /**
     * 输入型通道
     * @return
     */
    @Input("input")
    SubscribableChannel input();

}
