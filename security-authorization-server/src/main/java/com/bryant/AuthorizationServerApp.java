package com.bryant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class AuthorizationServerApp {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServerApp.class, args);
    }

}
