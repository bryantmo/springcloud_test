package com.bryant.flux;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class FluxBufferTest {

    public static void main(String[] args) {
        // buffer缓冲 - buffer 是流处理中非常常用的一种处理，意思就是将流的一段截停后再做处理。
        Flux.range(1, 13).buffer(20).subscribe(System.out::println);
        Flux.interval(Duration.of(0, ChronoUnit.SECONDS),
                        Duration.of(1, ChronoUnit.SECONDS))
                .buffer(Duration.of(5, ChronoUnit.SECONDS)).
                take(2).toStream().forEach(System.out::println);
        Flux.range(1, 10).bufferUntil(i -> i % 2 == 0)
                .subscribe(System.out::println);
        Flux.range(1, 10).bufferWhile(i -> i % 2 == 0)
                .subscribe(System.out::println);
    }
}
