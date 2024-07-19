package com.bryant.controller.feign;

import com.bryant.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserFeignController {

    @GetMapping("/feign/getUser")
    public String getUser(
            @RequestParam("name") String name
    ) {
        return "feign response:" + name;
    }

    @GetMapping("/feign/getUser2")
    public User getUser2(
            @RequestHeader("name") String name,
            @RequestHeader("id") Integer id
    ) {
        return new User(name, id);
    }

    @PostMapping("/feign/getUser3")
    public String getUser3(
            @RequestBody User user
    ) {
        return String.format("feign response: %s, %d",
                user.getName(), user.getUserId());
    }


}
