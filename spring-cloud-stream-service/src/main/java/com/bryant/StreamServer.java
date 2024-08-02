package com.bryant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class StreamServer {

    public static void main(String[] args) {
        SpringApplication.run(StreamServer.class, args);
    }

}
