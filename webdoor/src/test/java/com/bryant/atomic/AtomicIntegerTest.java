package com.bryant.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class AtomicIntegerTest {

    /**
     * 并发计数器
     */
    private static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) {
        int numberOfThreads = 20;
        Thread[] threads = new Thread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    long start = System.currentTimeMillis();
                    log.info(Thread.currentThread().getName() + ", time = " + j + " - start incrementAndGet, time = " + start);
                    counter.incrementAndGet();
                    log.info(Thread.currentThread().getName() + ", time = " + j + " - finish incrementAndGet, gap = "  + (System.currentTimeMillis() - start));
                }
            });
        }

        // 并发执行
        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        log.info("Final counter value: " + counter.get());
    }
}
