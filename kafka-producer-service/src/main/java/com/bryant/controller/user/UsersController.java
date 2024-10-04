package com.bryant.controller.user;

import com.bryant.model.Users;
import com.bryant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/user")
public class UsersController {

    @Autowired
    private UserService userService;

    @PostMapping("/batch_create")
    public void batchCreate() {
        List users = new ArrayList<Users>();
        for (int i=0; i < 10; i++) {
            Users u = Users.builder()
                    .age(i)
                    .email(new Random().nextLong() + "@qq.com")
                    .name("bryant" + i)
                    .tenantId(1L)
                    .build();
            users.add(u);
        }
        userService.sendKafkaMessage(users);
    }
}
