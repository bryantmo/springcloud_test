package com.bryant.flux;

import reactor.core.publisher.Flux;

public class FluxReduceTest {
    public static void main(String[] args) {
        //累积
        //reduce 和 reduceWith 操作符对流中包含的所有元素进行累积操作，得到一个包含计算结果的 Mono 序列。
        // 累积操作是通过一个 BiFunction 来表示的。reduceWith 允许在在操作时指定一个起始值(与第一个元素进行运算)
        Flux.range(1, 100).reduce((x, y) -> x + y)
                .subscribe(System.out::println);
        Flux.range(1, 100).reduceWith(() -> 100, (x, y) -> x + y)
                .subscribe(System.out::println);
    }
}
