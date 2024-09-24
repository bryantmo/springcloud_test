package com.bryant.util;

import java.util.concurrent.*;

public class ThreadPoolUtils {
    public static ThreadPoolExecutor newThreadPool(
            int corePoolSize,
            int maximumPoolSize,
            long keepAliveTime,
            TimeUnit unit,
            BlockingQueue<Runnable> workQueue,
            ThreadFactory threadFactory,
            RejectedExecutionHandler handler
    ){

        if (handler == null) {
            handler = new ThreadPoolExecutor.AbortPolicy();
        }

        if (threadFactory == null) {
            threadFactory = Executors.defaultThreadFactory();
        }

        return new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                threadFactory,
                handler
        );
    }
}
