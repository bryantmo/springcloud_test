package com.bryant.ttl;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;
import com.alibaba.ttl.threadpool.TtlExecutors;
import com.bryant.util.ThreadPoolUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class TestTransmittableThreadLocalV2 {

    private static TransmittableThreadLocal transmittableThreadLocal = new TransmittableThreadLocal();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        transmittableThreadLocal.set("main");
        log.info("main - threadLocal:{}", transmittableThreadLocal.get());

        new Thread(() -> {
            log.info("thread1 - threadLocal:{}", transmittableThreadLocal.get());
            transmittableThreadLocal.set("thread-1");
            log.info("thread1 - after set - threadLocal:{}", transmittableThreadLocal.get());
        }).start();

        ExecutorService ttlExecutorService = TtlExecutors.getTtlExecutorService(ThreadPoolUtils.newThreadPool(
                1, 1,
                0, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10), null, null));

        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() {

                // 线程池里面的线程
                log.info("threadPoolExecutor - task - threadLocal:{}", transmittableThreadLocal.get());

                // 线程池里面的线程再new线程
                new Thread(() -> {
                    log.info("threadPoolExecutor - task - thread - threadLocal:{}", transmittableThreadLocal.get());
                }).start();

                return "finish";
            }
        };

        Future<?> submit = ttlExecutorService.submit(callable);
        if (!submit.isCancelled()) {
            System.out.println("result = "+submit.get());
        }

    }
}
