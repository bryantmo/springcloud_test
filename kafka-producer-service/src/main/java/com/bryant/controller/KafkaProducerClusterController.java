package com.bryant.controller;

import com.bryant.config.KafkaProducerProperties;
import com.bryant.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka_cluster")
public class KafkaProducerClusterController {

    @Autowired
    private KafkaService kafkaService;

    @Autowired
    private KafkaProducerProperties kafkaProducerProperties;

    @PostMapping("/multi_partitions")
    public void batchCreateMessageAsyn(
            @RequestParam("message") String message,
            @RequestParam("key") Integer key)
    {
        kafkaService.createMessageAsyn(kafkaProducerProperties.getMultiPartitionTopic(), message, key);
    }
}
