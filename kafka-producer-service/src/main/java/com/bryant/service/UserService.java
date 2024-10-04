package com.bryant.service;

import com.bryant.model.Users;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private KafkaService kafkaService;

    public void sendKafkaMessage(List<Users> users) {

        if (CollectionUtils.isEmpty(users)) {
            return;
        }

        // 批量发消息
        kafkaService.batchSendMessages(users);

    }
}
