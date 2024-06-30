package com.bryant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class WebDoorServer {

    public static void main(String[] args) {
        SpringApplication.run(WebDoorServer.class, args);
    }

}
