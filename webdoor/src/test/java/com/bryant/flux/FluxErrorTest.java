package com.bryant.flux;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FluxErrorTest {
    public static void main(String[] args) {
        //异常处理
        //在前面所提及的这些功能基本都属于正常的流处理，然而对于异常的捕获以及采取一些修正手段也是同样重要的。
        //利用Flux/Mono 框架可以很方便的做到这点。
//        Flux.just(1, 2)
//                .concatWith(Mono.error(new IllegalStateException("人为错误")))
//                .subscribe(System.out::println, System.err::println);

//        Flux.just(1,2,3,4)
//                .concatWith(Mono.error(new IllegalStateException()))
//                .onErrorReturn(10000)
//                .subscribe(System.out::println);

        // 自定义异常
        Flux.just(1, 2)
                .concatWith(Mono.error(new IllegalArgumentException()))
                .onErrorResume(e -> {
                    if (e instanceof IllegalStateException) {
                        return Mono.just(0);
                    } else if (e instanceof IllegalArgumentException) {
                        return Mono.just(-1);
                    }
                    return Mono.empty();
                })
                .subscribe(System.out::println);

        //当产生错误时重试
        Flux.just(1, 2)
                .concatWith(Mono.error(new IllegalStateException()))
                .retry(1)
                .subscribe(System.out::println);
    }
}
