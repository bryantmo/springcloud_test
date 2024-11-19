package com.bryant.syntax;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadTest {
    public static void main(String[] args) {
        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                log.info("runnable");
            }
        };

        Thread thread = new Thread(runnable) {
            // 覆盖实现了run方法
            @Override
            public void run() {
                log.info("thread");
            }
        };

        thread.run();
    }
}
