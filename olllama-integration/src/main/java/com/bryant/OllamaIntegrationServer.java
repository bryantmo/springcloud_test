package com.bryant;

import java.time.Duration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 通过SpringAI框架，集成ollama大模型，并请求
 * 参考：
 *  - https://blog.csdn.net/weixin_54925172/article/details/138815936
 *  -
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.bryant.mapper")
public class OllamaIntegrationServer {

    public static void main(String[] args) {
        SpringApplication.run(OllamaIntegrationServer.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

    /**
     * 带超时时间的RestTemplate
     * @param restTemplateBuilder
     * @return
     */
    @Bean("timeOutRestTemplate")
    public RestTemplate restTemplate2(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(60))
                .setReadTimeout(Duration.ofSeconds(60))
                .build();
    }
}
