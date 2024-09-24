package com.bryant.ttl;

import com.bryant.util.ThreadPoolUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TestThreadLocal {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        threadLocal.set("main");
        log.info("main - threadLocal:{}", threadLocal.get());

        // 主线程里面的线程
        new Thread(() -> {
            log.info("main - thread - threadLocal:{}", threadLocal.get());
        }).start();

        ThreadPoolExecutor threadPoolExecutor = ThreadPoolUtils.newThreadPool(
                1, 1,
                0, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10), null, null);

        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                // 线程池里面的线程
                log.info("threadPoolExecutor - task - threadLocal:{}", threadLocal.get());          threadLocal.set("thread-1");

                // 线程池里面的线程再new线程
                new Thread(() -> {
                    log.info("threadPoolExecutor - task - thread - threadLocal:{}", threadLocal.get());
                }).start();

            }
        });

    }
}
