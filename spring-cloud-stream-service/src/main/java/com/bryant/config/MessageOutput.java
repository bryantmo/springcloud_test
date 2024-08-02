package com.bryant.config;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MessageOutput {

    /**
     * 输出型通道
     * @return
     */
    @Output("output")
    MessageChannel output();

}
