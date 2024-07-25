package com.bryant.controller;

import com.bryant.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class HelloWebFluxController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, WebFlux !";
    }

    /**
     * 这里注意，User 对象是通过 Mono 对象包装的，你可能会问，为啥不直接返回呢？
     *
     * 在 WebFlux 中，Mono 是非阻塞的写法，只有这样，你才能发挥 WebFlux 非阻塞 + 异步的特性。
     *
     * 补充：在 WebFlux 中，除了 Mono 外，还有一个 Flux，这哥俩均能充当响应式编程中[发布者的角色]，不同的是：
     *
     * Mono：返回 0 或 1 个元素，即单个对象。
     * Flux：返回 N 个元素，即 List 列表对象。
     * @return
     */
    @GetMapping("/user")
    public Mono<User> getUser() {
        User user = new User();
        user.setName("犬小哈");
        user.setDesc("欢迎关注我的公众号: 小哈学Java");
        return Mono.just(user);
    }

    @GetMapping("/users")
    public Flux<User> getUsers() {
        User user = new User();
        user.setName("犬小哈");
        user.setDesc("欢迎关注我的公众号: 小哈学Java");
        User user2 = new User();
        user2.setName("犬小哈");
        user2.setDesc("欢迎关注我的公众号: 小哈学Java");
        return Flux.just(user, user2);
    }



}