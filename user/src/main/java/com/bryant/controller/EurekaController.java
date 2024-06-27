package com.bryant.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EurekaController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/client_fetch_services")
    private List<String> fetchServices() {
        return discoveryClient.getServices();
    }

}
