package org.example.agent;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.agent.util.JvmStack;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class MyAgent implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        premain(null, null);
        return classfileBuffer;
    }

    public static void premain(String agentArgs, Instrumentation inst) {
        log.info("this is my agent：{}", agentArgs);

        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            @SneakyThrows
            public void run() {
                Thread.sleep(1000);
                JvmStack.printMemoryInfo();
                JvmStack.printGCInfo();
                log.info("===================================================================================================");
            }
        }, 0, 5000, TimeUnit.MILLISECONDS);
    }

}