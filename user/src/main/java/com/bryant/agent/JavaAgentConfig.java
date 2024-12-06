package com.bryant.agent;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaAgentConfig {

    @Bean
    public TestAgentBean testAgentBean() {
        return new TestAgentBean("1111");
    }
}
