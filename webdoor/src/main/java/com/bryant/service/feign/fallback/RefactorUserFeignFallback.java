package com.bryant.service.feign.fallback;

import com.bryant.dto.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 服务降级逻辑，为Feign客户端的定义接口，编写一个具体的接口实现类
 * 每个重写方法的实现逻辑，都可以用来定义相应的服务降级逻辑
 */
@Component
public class RefactorUserFeignFallback implements UserFeignHystrixService {

    @Override
    public String getName() {
        return "RefactorUserFeignFallback, getName fallback";
    }

    @Override
    public String getUser(@RequestParam("name") String name) throws Exception {
        return "RefactorUserFeignFallback, getUser fallback";
    }

    @Override
    public User getUser2(
            @RequestHeader("name") String name,
            @RequestHeader("id") Integer id) {
         return new User(11, "RefactorUserFeignFallback, getUser2 fallback");
    }

    @Override
    public String getUser3(@RequestBody User user) {
        return "RefactorUserFeignFallback, getUser3 fallback";
    }

    @Override
    public String testHystrixV2() {
        return "RefactorUserFeignFallback, testHystrix fallback";
    }
}
