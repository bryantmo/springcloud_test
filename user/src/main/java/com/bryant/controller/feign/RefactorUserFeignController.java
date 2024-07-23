package com.bryant.controller.feign;

import com.bryant.dto.User;
import com.bryant.service.UserFeignService;
import java.util.Random;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通过继承，实现UserFeignService接口，不再包含请求映射注解@mRequestMapping
 * 而参数的注解定义，会自动在重写时带过来
 */
@RestController
@Log4j2
public class RefactorUserFeignController implements UserFeignService {

    @Override
    public String getName() {
        return "refactor response: " + "bryant";
    }

    @Override
    public String getUser(@RequestParam("name") String name) throws Exception {
        int sleepTime = new Random().nextInt(3000);
        log.info("[users#getUser] feign sleep time: " + sleepTime);
        Thread.sleep(sleepTime);

        return "feign response:" + name;
    }

    @Override
    public User getUser2(
            @RequestHeader("name") String name,
            @RequestHeader("id") Integer id) {
        return new User(id, name);
    }

    @Override
    public String getUser3(
            @RequestBody User user) {
        return String.format("feign response: %s, %d",
                user.getName(), user.getUserId());
    }

    @Override
    public String testHystrixV2() {
        int sleepTime = new Random().nextInt(100000);
        log.info("[users#testHystrix] feign sleep time: " + sleepTime);
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
        }
        return "feign response: hystrix";
    }
}
