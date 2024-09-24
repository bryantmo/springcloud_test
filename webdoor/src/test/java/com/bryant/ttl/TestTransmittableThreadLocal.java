package com.bryant.ttl;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;
import com.bryant.util.ThreadPoolUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TestTransmittableThreadLocal {

    private static TransmittableThreadLocal transmittableThreadLocal = new TransmittableThreadLocal();

    public static void main(String[] args) {
        transmittableThreadLocal.set("main");
        log.info("main - threadLocal:{}", transmittableThreadLocal.get());

        new Thread(() -> {
            log.info("thread1 - threadLocal:{}", transmittableThreadLocal.get());
            transmittableThreadLocal.set("thread-1");
            log.info("thread1 - after set - threadLocal:{}", transmittableThreadLocal.get());
        }).start();

        ThreadPoolExecutor threadPoolExecutor = ThreadPoolUtils.newThreadPool(
                1, 1,
                0, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10), null, null);

        // 构造一个新的TtlRunnable
        TtlRunnable ttlRunnable = TtlRunnable.get(new Runnable() {
            @Override
            public void run() {
                // 线程池里面的线程
                log.info("threadPoolExecutor - task - threadLocal:{}", transmittableThreadLocal.get());

                // 线程池里面的线程再new线程
                new Thread(() -> {
                    log.info("threadPoolExecutor - task - thread - threadLocal:{}", transmittableThreadLocal.get());
                }).start();

            }
        });
        threadPoolExecutor.execute(ttlRunnable);

        transmittableThreadLocal.set("main2");
        log.info("main - threadLocal:{}", transmittableThreadLocal.get());

        // 构造一个新的TtlRunnable
        TtlRunnable ttlRunnable2 =TtlRunnable.get(new Runnable() {
            @Override
            public void run() {
                // 线程池里面的线程
                log.info("threadPoolExecutor - task2 - threadLocal:{}", transmittableThreadLocal.get());

                // 线程池里面的线程再new线程
                new Thread(() -> {
                    log.info("threadPoolExecutor - task2 - new thread - threadLocal:{}", transmittableThreadLocal.get());
                }).start();

            }
        });
        threadPoolExecutor.execute(ttlRunnable2);

    }
}
