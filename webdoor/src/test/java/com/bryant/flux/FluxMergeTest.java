package com.bryant.flux;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class FluxMergeTest {

    public static void main(String[] args) {
        // 合并 -- 两个流重叠部分才能合并，
//        注意到zipWith是分别按照元素在流中的顺序进行两两合并的，合并后的流长度则最短的流为准，取交集，遵循最短对齐原则。

//        Flux.just("I", "You")
//                .zipWith(Flux.just("Win", "Lose", "Haha"))
//                .subscribe(System.out::println);
//        Flux.just("I", "You")
//                .zipWith(Flux.just("Win", "Lose"),
//                        (s1, s2) -> String.format("%s!%s!", s1, s2))
//                .subscribe(System.out::println);

        // 合流
        // 与合并比较类似的处理概念是合流，合流的不同之处就在于元素之间不会产生合并，最终流的元素个数(长度)是两个源的个数之和。
        // 合流的计算可以使用 merge或mergeSequential 函数，这两者的区别在于：
        // merge后的元素是按产生时间排序的，而mergeSequential 则是按整个流被订阅的时间来排序
//        Flux.merge(Flux.interval(
//                                //delay 延迟
//                                Duration.of(0, ChronoUnit.MILLIS),
//                                //interval 产生元素
//                                Duration.of(100, ChronoUnit.MILLIS)).take(2),
//                        Flux.interval(
//                                Duration.of(50, ChronoUnit.MILLIS),
//                                Duration.of(100, ChronoUnit.MILLIS)).take(2))
//                .toStream()
//                .forEach(System.out::println);
//        System.out.println("---");
//        Flux.mergeSequential(Flux.interval(
//                                Duration.of(0, ChronoUnit.MILLIS),
//                                Duration.of(100, ChronoUnit.MILLIS)).take(2),
//                        Flux.interval(
//                                Duration.of(50, ChronoUnit.MILLIS),
//                                Duration.of(100, ChronoUnit.MILLIS)).take(2))
//                .toStream()
//                .forEach(System.out::println);

        // flatMap -- 与合并比较类似的处理概念是合流，合流的不同之处就在于元素之间不会产生合并，最终流的元素个数(长度)是两个源的个数之和。合流的计算可以使用 merge或mergeSequential 函数，这两者的区别在于：
        //merge后的元素是按产生时间排序的，而mergeSequential 则是按整个流被订阅的时间来排序
//        Flux.just(1, 2, 3)
//                .flatMap(x -> Flux.interval(
//                                    Duration.of(x * 1000, ChronoUnit.MILLIS),
//                                    Duration.of(10, ChronoUnit.MILLIS)
//                                )
//                                .take(x)//拿出前x个，1，2，3
//                                .takeWhile(k -> k <2)//满足条件元素<2才能继续
//                        )
//                .toStream()
//                .forEach(System.out::println);

        //Flux.interval就是一个可以源源不断，往管道里面产生元素的Flux
//        Flux.interval(
//                Duration.of(0 * 1000, ChronoUnit.MILLIS), //延迟进行后续操作
//                Duration.of(1000, ChronoUnit.MILLIS) //每1000ms产生一个元素
//        )
//                .take(2)//必须拿出2个，实际要等2s才能完成
//                .toStream().forEach(System.out::println);
    }
}
