package com.bryant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 通过SpringAI框架，集成ollama大模型，并请求
 * 参考：
 *  - https://blog.csdn.net/weixin_54925172/article/details/138815936
 *  -
 */
@SpringBootApplication
public class OllamaIntegrationServer {

    public static void main(String[] args) {
        SpringApplication.run(OllamaIntegrationServer.class, args);
    }

}
