package com.bryant.jvm;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 两个线程的细粒度调度
 */
@Slf4j
public class ConditionExample {
    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();
    private static boolean ready = false;

    public static void main(String[] args) throws InterruptedException {

        Thread waiter = new Thread(() -> {
            try {
                log.info("Waiter try get lock");
//                lock.lock();
                log.info("Waiter get lock");
                try {
                    while (!ready) {
                        log.info(Thread.currentThread().getName() + " is waiting.");
                        // 线程进入等待状态
                        condition.await();
                    }
                    log.info(Thread.currentThread().getName() + " is proceeding.");
                } finally {
//                    lock.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Waiter");

        Thread notifier = new Thread(
                () -> {
                    log.info("Notifier try get lock");
                    lock.lock();
                    log.info("Notifier get lock");
                    try {
                        ready = true;
                        log.info(Thread.currentThread().getName() + " is notifying.");
                        // 唤醒所有等待的线程
                        condition.signalAll();
                    } finally {
                        lock.unlock();
                    }
                },
                "Notifier");

        waiter.start();
        Thread.sleep(1000);
        notifier.start();
    }

}