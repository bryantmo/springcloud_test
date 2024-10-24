package com.bryant.jvm;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 三个线程的细粒度调度
 */
@Slf4j
public class TestRentrantLock {

    // fair lock
    private static ReentrantLock lock = new ReentrantLock(true);

    // unfair lock
    private static ReentrantLock unfairLock = new ReentrantLock(true);

    private static Boolean loop = false;

    public static void main(String[] args) throws InterruptedException {

        Condition condition = lock.newCondition();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock.lock();
                    log.info("task1 start");

                    while (!loop) {
                        condition.await();

                        Thread.sleep(1000);
                        condition.signalAll();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
                log.info("task1 finish");
            }
        });

        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock.lock();
                    log.info("task3 start");

                    while (!loop) {
                        condition.await();

                        Thread.sleep(1000);
                        condition.signalAll();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
                log.info("task3 finish");
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock.lock();
                    log.info("task2 start");
                    loop = true;
                    condition.signal();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
                log.info("task2 finish");
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();


        log.info("main finish");
    }
}
