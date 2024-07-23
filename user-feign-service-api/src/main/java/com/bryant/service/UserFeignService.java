package com.bryant.service;

import com.bryant.dto.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 服务名不区分大小写
 */
@RequestMapping("/refactor")
public interface UserFeignService {

    @GetMapping("/v2/getName")
    String getName();

    @GetMapping("/v2/feign/getUser")
    String getUser(@RequestParam("name") String name) throws Exception;

    @GetMapping("/v2/feign/getUser2")
    User getUser2(
            @RequestHeader("name") String name,
            @RequestHeader("id") Integer id);

    @PostMapping("/v2/feign/getUser3")
    String getUser3(@RequestBody User user);

    @GetMapping("/v2/feign/hystrix")
    String testHystrixV2();

}
