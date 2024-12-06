package org.example.agent.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JvmRuntime {

    public static void printRuntimeInfo(){
        StringBuilder sb = new StringBuilder();
        // 返回JVM可用的处理器数目
        int processors = Runtime.getRuntime().availableProcessors();
        sb.append("处理器数目：" + processors).append(", ");

        // 启动记事本编辑器
        //                Process process = Runtime.getRuntime().exec("notepad.exe");

        // 返回JVM当前空闲的内存量
        long freeMemory = Runtime.getRuntime().freeMemory();
        sb.append("空闲内存：" + freeMemory).append(", ");

        // 返回JVM当前总共可用的内存量
        long totalMemory = Runtime.getRuntime().totalMemory();
        sb.append("总共可用内存：" + totalMemory).append(", ");

        // 强制执行垃圾回收
        Runtime.getRuntime().gc();

        // 强制结束JVM的运行
        Runtime.getRuntime().exit(0);

        // 注册一个线程，在JVM关闭时执行
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("JVM正在关闭...");
        }));

        log.info("Agent1 -- {}", sb.toString());
    }
}
