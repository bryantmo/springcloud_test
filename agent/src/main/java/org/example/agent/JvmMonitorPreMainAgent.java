package org.example.agent;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.agent.transformer.ClassTransformer;
import org.example.agent.util.JvmStack;

import java.lang.instrument.Instrumentation;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class JvmMonitorPreMainAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        log.info("this is my agent - premain：{}", agentArgs);

        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            @SneakyThrows
            public void run() {
                JvmStack.printMemoryInfo();
                JvmStack.printGCInfo();
                log.info("===================================================================================================");
            }
        }, 0, 5000, TimeUnit.MILLISECONDS);
    }

}