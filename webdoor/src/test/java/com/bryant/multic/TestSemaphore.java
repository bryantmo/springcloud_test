package com.bryant.multic;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestSemaphore {

    private static final Semaphore semaphore = new Semaphore(1);

    public static void main(String[] args) {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (!semaphore.tryAcquire(10, TimeUnit.SECONDS)) {
                        log.info("waiting-1..");
                    } else {
//                        semaphore.release(1);
                    }
                    log.info("finish-1..");
                } catch (InterruptedException e) {
                    log.info("exception-1", e);
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (!semaphore.tryAcquire(10, TimeUnit.SECONDS)) {
                        log.info("waiting-2..");
                    } else {
//                        semaphore.release(1);
                    }
                    log.info("finish-2..");
                } catch (InterruptedException e) {
                    log.info("exception-2", e);
                }
            }
        });

        t1.start();
        t2.start();
    }

}