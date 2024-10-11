package com.bryant.multic;

import lombok.extern.slf4j.Slf4j;

/**
 * notify和notifyAll
 *
 * 竞争synchronized锁，多个线程会被阻塞，然后竞争锁，唤醒一个线程，然后等待被唤醒的线程释放锁，再竞争锁，唤醒另一个线程，如此循环。
 */
@Slf4j
public class TestThreadNotify {

    private static Object lock = new Object();

    private static volatile boolean flag = false;

    public static void main(String[] args) {

        Thread thread1 = new Thread() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        log.info("thread1 sleep do not release cpu resource...");
                        Thread.sleep(6000);
                        flag = true;
                        log.info("thread1 finish, release cpu resource...");
                        // 唤醒一个，正在等待的线程
                        lock.notify();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        };
        thread1.start();

        Thread thread2 = new Thread() {
            @Override
            public void run() {
                // 重量级锁，此处会阻塞，然后t2进入blocked状态
                synchronized (lock) {
                    if (flag) {
                        log.info("thread2 wait...");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        log.info("thread2 finish, release cpu resource...");
                    }
                    //唤醒所有的等待线程（此时只有t3）
                    lock.notifyAll();
                }
            }
        };
        thread2.start();

        Thread thread3 = new Thread() {
            @Override
            public void run() {
                // 重量级锁，此处会阻塞，然后t3进入blocked状态
                synchronized (lock) {
                    if (flag) {
                        log.info("thread3 wait...");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        log.info("thread3 finish, release cpu resource...");
                    }
                }
            }
        };
        thread3.start();

    }
}
