package com.bryant.ttl;

import com.bryant.util.ThreadPoolUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class TestInheritableThreadLocal {

    private static InheritableThreadLocal inheritableThreadLocal = new InheritableThreadLocal();

    public static void main(String[] args) {
        inheritableThreadLocal.set("main");
        log.info("main - threadLocal:{}", inheritableThreadLocal.get());

        new Thread(() -> {
            log.info("main - thread - threadLocal:{}", inheritableThreadLocal.get());
        }).start();

        ThreadPoolExecutor threadPoolExecutor = ThreadPoolUtils.newThreadPool(
                1, 1,
                0, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10), null, null);

        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                // 线程池里面的线程
                log.info("threadPoolExecutor - task1 - threadLocal:{}", inheritableThreadLocal.get());

                // 线程池里面的线程再new线程
                new Thread(() -> {
                    log.info("threadPoolExecutor - task1 - new thread - threadLocal:{}", inheritableThreadLocal.get());
                }).start();

            }
        });

        inheritableThreadLocal.set("main2");
        log.info("main - threadLocal:{}", inheritableThreadLocal.get());

        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                // 线程池里面的线程
                log.info("threadPoolExecutor - task2 - threadLocal:{}", inheritableThreadLocal.get());

                // 线程池里面的线程再new线程
                new Thread(() -> {
                    log.info("threadPoolExecutor - task2 - new thread - threadLocal:{}", inheritableThreadLocal.get());
                }).start();

            }
        });

    }
}
