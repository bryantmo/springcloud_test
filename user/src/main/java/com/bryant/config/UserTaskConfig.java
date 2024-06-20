package com.bryant.config;

import com.bryant.bean.UserTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "user.config", name = "task.enable", havingValue = "true")
public class UserTaskConfig {

    @Bean
    public UserTask userTask() {
        log.info("userTask bean init..");
        UserTask userTask = new UserTask();
        userTask.setName("userTask");
        userTask.setId(11L);
        userTask.setAge(30);
        return userTask;
    }

}
