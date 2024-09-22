package com.bryant.multic;

public class ThreadLocalTest {
  public static void main(String[] args) throws InterruptedException {
    //主线程设置两个 ThreadLocal 变量
    final ThreadLocal threadLocal = new ThreadLocal();
//    final ThreadLocal threadLocal2 = new ThreadLocal();
    threadLocal.set("test");
//    threadLocal2.set(1111);
 
 
    Thread t1 = new Thread(new Runnable() {
      @Override
      public void run() {
        //子线程1赋值（两个 ThreadLocal 变量）
        threadLocal.set("test 1");
//        threadLocal2.set(1112);
        System.out.println("thread1 - threadLocal.value = " + threadLocal.get());
//        System.out.println("thread1 - threadLocal2.value = " + threadLocal2.get());
      }
    });
 
 
    Thread t2 = new Thread(new Runnable() {
      @Override
      public void run() {
        //子线程2赋值（两个 ThreadLocal 变量）
        threadLocal.set("test 2");
//        threadLocal2.set(1113);
        System.out.println("thread2 - threadLocal.value = " +threadLocal.get());
//        System.out.println("thread2 - threadLocal2.value = " +threadLocal2.get());
      }
    });
    t1.start();
    t2.start();
 
    Thread.sleep(1000);
    System.out.println("main - threadLocal.value = " + threadLocal.get());
//    System.out.println("main - threadLocal2.value = " + threadLocal2.get());
  }
}