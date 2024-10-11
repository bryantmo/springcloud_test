package com.bryant.multic;

import com.bryant.util.ThreadPoolUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * CountDownLatch
 * 倒计时，拦截某个线程（CountDownLatch.await()），当倒计时为0时，将线程放行
 */
@Slf4j
public class TestCountDownLatch {

    private static final CountDownLatch countDownLatch = new CountDownLatch(1);
    private static final ThreadPoolExecutor threadPoolExecutor = ThreadPoolUtils.newThreadPool(
            1,
            1,
            0,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1), null, null
    );

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            try {
                log.info("thread finish execute... count = {}", countDownLatch.getCount());
                // 阻塞
                countDownLatch.await();
                log.info("thread finish execute... count = {}", countDownLatch.getCount());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        threadPoolExecutor.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        log.info("threadPoolExecutor execute task... count = {}", countDownLatch.getCount());
                        // 减一
                        countDownLatch.countDown();
                        log.info("threadPoolExecutor execute task... count = {}", countDownLatch.getCount());
                    }
                }
        );


        Thread.sleep(1000);
        threadPoolExecutor.shutdownNow();

    }
}
