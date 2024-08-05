package com.bryant.service.feign.fallback;

import com.bryant.dto.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * （1）configuration = DisableHystrixConfiguration，关闭Hystrix；
 * 但是，feign.hystrix.enabled=true的全局配置生效时，还是会开启Hystrix；
 * （2）RefactorUserFeignFallback是服务降级实现类
 * @FeignClient绑定服务
 * 继承UserFeignService接口
 */
@FeignClient(name = "users3", fallback = RefactorUserFeignFallback.class)
public interface UserFeignHystrixService {

    @GetMapping("/refactor/v2/getName")
    String getName();

    @GetMapping("/refactor/v2/feign/getUser")
    String getUser(@RequestParam("name") String name) throws Exception;

    @GetMapping("/refactor/v2/feign/getUser2")
    User getUser2(
            @RequestHeader("name") String name,
            @RequestHeader("id") Integer id);

    @PostMapping("/refactor/v2/feign/getUser3")
    String getUser3(@RequestBody User user);

    @GetMapping("/refactor/v2/feign/hystrix")
    String testHystrixV2();

}
