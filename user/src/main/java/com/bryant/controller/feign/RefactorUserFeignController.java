package com.bryant.controller.feign;

import com.bryant.dto.User;
import com.bryant.service.UserFeignService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通过继承，实现UserFeignService接口，不再包含请求映射注解@mRequestMapping
 * 而参数的注解定义，会自动在重写时带过来
 */
@RestController
public class RefactorUserFeignController implements UserFeignService {

    @Override
    public String getName() {
        return "refactor response: " + "bryant";
    }

    @Override
    public String getUser(@RequestParam("name") String name) {
        return "feign response:" + name;
    }

    @Override
    public User getUser2(@RequestHeader("name") String name,
            @RequestHeader("id") Integer id) {
        return new User(id, name);
    }

    @Override
    public String getUser3(
            @RequestBody User user) {
        return String.format("feign response: %s, %d",
                user.getName(), user.getUserId());
    }
}
