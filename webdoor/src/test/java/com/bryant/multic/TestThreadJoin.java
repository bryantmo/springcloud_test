package com.bryant.multic;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestThreadJoin {
    public static void main(String[] args) throws InterruptedException {


        Thread thread_run1 = new Thread() {
            @Override
            public void run() {
                System.out.println("thread run1");
                try {
                    // 休眠5s
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("thread run1 finish");
            }
        };

        Thread thread_run2 = new Thread() {
            @Override
            public void run() {
                System.out.println("thread run2");
                try {
                    // 阻塞等待 thread_run1 执行完成
                    thread_run1.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("thread run2 finish");
            }
        };


        Thread thread_run3 = new Thread() {
            @Override
            public void run() {
                System.out.println("thread run3");
                try {
                    // 阻塞等待 thread_run2 执行完成
                    thread_run2.join();
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("thread run3 finish");
            }
        };


        thread_run1.start();
        thread_run2.start();
        thread_run3.start();

        // main 阻塞等待 thread_run1、thread_run2 执行完成
        thread_run1.join();
        thread_run2.join();
        log.info("main thread finish...（do not wait thread3）");
    }
}
