package com.bryant.service.feign;

import com.bryant.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 服务名不区分大小写
 */
@FeignClient(name = "users")
// 权宜之计，为了避免和RefactorUserFeignService冲突
public interface UserFeignService {

    @GetMapping("/getName")
    String getName();

    @GetMapping("/feign/getUser")
    String getUser(@RequestParam("name") String name);

    @GetMapping("/feign/getUser2")
    String getUser2(
            @RequestHeader("name") String name,
            @RequestHeader("id") Integer id);

    @PostMapping("/feign/getUser3")
    String getUser3(@RequestBody User user);

}
