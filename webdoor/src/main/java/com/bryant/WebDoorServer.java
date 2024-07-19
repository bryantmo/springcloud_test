package com.bryant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EnableCircuitBreaker
public class WebDoorServer {

    public static void main(String[] args) {
        SpringApplication.run(WebDoorServer.class, args);
    }

}
