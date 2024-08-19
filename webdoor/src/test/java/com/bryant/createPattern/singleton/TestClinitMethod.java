package com.bryant.createPattern.singleton;

public class TestClinitMethod {

    // 静态代码块，有且只会被JVM编译执行一次
    static {
        if (true) {
            System.out.println(Thread.currentThread() + "init TestClinitMethod, check out whether static code block can be executed several times...");
            while (true) {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                System.out.println(Thread.currentThread() + "线程死循环");
            }
        }
    }

    public static void main(String[] args) {
        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                System.out.println(Thread.currentThread() + " start...");
                TestClinitMethod testClinitMethod = new TestClinitMethod();
                System.out.println(Thread.currentThread() + " end...");
            }
        };

        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);

        thread1.start();
        thread2.start();
    }
}
