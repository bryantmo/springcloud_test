package org.example.agent;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.agent.util.JvmRuntime;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
public class Agent1 implements ClassFileTransformer {

    private static final ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(5, 5, 60, java.util.concurrent.TimeUnit.SECONDS, new java.util.concurrent.LinkedBlockingQueue<Runnable>());
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        premain(null);
        return classfileBuffer;
    }

    public static void premain(String args){
        System.out.println("Agent1 premain");
        threadPoolExecutor.execute(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                jvmDetect();
            }

            private void jvmDetect() throws IOException, InterruptedException {

                while(true) {
                    JvmRuntime.printRuntimeInfo();
                    Thread.sleep(1000);
                }
            }
        });
    }
}
