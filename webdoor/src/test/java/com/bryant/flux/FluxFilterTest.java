package com.bryant.flux;

import reactor.core.publisher.Flux;

public class FluxFilterTest {
    public static void main(String[] args) {
        // filter 函数：true 满足则输出，false 不满足则不输出
//        Flux.range(1, 10).filter(i -> i % 2 == 0).subscribe(System.out::println);

//        take 函数 可以用来提取想要的元素，这与filter 过滤动作是恰恰相反的
        // 第一个take(2)指提取前面的两个元素
//        Flux.range(1, 10).take(2).subscribe(System.out::println);
//        // 第二个takeLast(2)指提取最后的两个元素
//        Flux.range(1, 10).takeLast(2).subscribe(System.out::println);
//        // 第三个takeWhile(Predicate p)指提取满足条件的元素
//        Flux.range(1, 10).takeWhile(i -> i < 5).subscribe(System.out::println);
//        // 第四个takeUtil(Predicate p)指一直提取直到满足条件的元素出现为止
//        Flux.range(1, 10).takeUntil(i -> i == 6).subscribe(System.out::println);
        // 提前满足，则提前退出
        Flux.range(1, 10).takeUntil(i -> i < 6).subscribe(System.out::println);
    }
}
