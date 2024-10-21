package com.bryant.jvm;

public class WaitNotifyExample {
    private final Object lock = new Object();

    public void doWait() {
        synchronized (lock) {
            try {
                System.out.println(Thread.currentThread().getName() + " is waiting.");
                lock.wait(); // 线程进入等待池
                System.out.println(Thread.currentThread().getName() + " is awakened.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void doNotify() {
        synchronized (lock) {
            System.out.println(Thread.currentThread().getName() + " is notifying.");
            lock.notify(); // 唤醒等待池中的一个线程
        }
    }

    public static void main(String[] args) {
        WaitNotifyExample example = new WaitNotifyExample();
        Thread waiter = new Thread(() -> example.doWait(), "Waiter");
        Thread notifier = new Thread(() -> example.doNotify(), "Notifier");
        waiter.start();
        notifier.start();
    }
}