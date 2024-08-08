package com.bryant.controller;

import com.bryant.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ResourceController {

    /**
     * 受保护的资源
     * @param userId
     * @return
     */
    @GetMapping("/users/{userId}")
    public User getUserInfo(
            @PathVariable("userId") String userId
    ) {
        log.info("get user info, userId: {}", userId);
        return new User("user", 11);
    }

    /**
     * 非受保护的资源
     */
    @GetMapping("/instance/{serviceId}")
    public String getInstanceInfo(
            @PathVariable("serviceId") String serviceId
    ) {
        log.info("get instance info, serviceId: {}", serviceId);
        return "serviceid   : " + serviceId;
    }
}
