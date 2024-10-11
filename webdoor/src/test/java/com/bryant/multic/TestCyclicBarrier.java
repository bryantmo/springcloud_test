package com.bryant.multic;

import com.bryant.util.ThreadPoolUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * CyclicBarrier
 * 拦截多个线程(CyclicBarrier.await)，当所有线程都到达时，各个阻塞线程将一起执行
 */
@Slf4j
public class TestCyclicBarrier {

    private static final CyclicBarrier cyclicBarrier = new CyclicBarrier(5);

    private static final ThreadPoolExecutor threadPo = ThreadPoolUtils.newThreadPool(
            5,
            5,
            0,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(10),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "TestCyclicBarrier-" + r.hashCode());
                }
            },
            new ThreadPoolExecutor.AbortPolicy());
    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {

        for (int i = 0; i < 5; i++) {
            threadPo.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        log.info("pool start... c = {}", cyclicBarrier.getParties());
                        cyclicBarrier.await();
                        log.info("pool end... c = {}", cyclicBarrier.getParties());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (BrokenBarrierException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

        log.info("start... c = {}", cyclicBarrier.getParties());
        Thread.sleep(10000);
        log.info("start... c = {}", cyclicBarrier.getParties());
        threadPo.shutdownNow();
    }
}
