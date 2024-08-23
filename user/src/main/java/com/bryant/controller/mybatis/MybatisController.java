package com.bryant.controller.mybatis;

import com.bryant.model.UserDetail;
import com.bryant.service.UserService;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MybatisController {

    @Autowired
    private UserService userService;

    @PostMapping("/user_insert")
    public UserDetail user_insert() {
        UserDetail detail = UserDetail.builder()
                .age(new Random().nextInt(100))
                .email(new Random().nextInt(100000000) + "@qq.com")
                .name("bryant" + new Random().nextInt(1111)).build();
        return userService.insert(detail);
    }

    @GetMapping("/user_select")
    public UserDetail user_select(@RequestParam("id") Long id) {
        return userService.getById(id);
    }

}
