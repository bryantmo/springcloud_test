package com.bryant.multic;

import com.bryant.util.ThreadPoolUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * Semaphore 限流线程池
 */
@Slf4j
public class TestSemephoreThreadPool {

    private static final Semaphore semaphore= new Semaphore(10);

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = ThreadPoolUtils.newThreadPool(5, 20, 0 , TimeUnit.SECONDS, new LinkedBlockingQueue<>(100), null, null);


        for (int i = 0; i < 20; i++) {
            int finalI = i;
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.tryAcquire(1000, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    log.info("threadPoolExecutor execute task... i = {}", finalI);
                    semaphore.release(1);
                }
            });
        }

//        threadPoolExecutor.shutdownNow();
    }
}
