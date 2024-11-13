package org.example.agent;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.agent.transformer.ClassTransformer;
import org.example.agent.util.JvmStack;

import java.lang.instrument.Instrumentation;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class JvmMonitorAgent {

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

    public static void agentmain(String agentArgs, Instrumentation inst){
        log.info("this is my agent - agentmain：{}", inst);

        //针对运行期的类进行增强
        inst.addTransformer(new ClassTransformer(),true);
        //agentmain运行时 由于堆里已经存在Class文件，所以新添加Transformer后
        // 还要再调用一个  inst.retransformClasses(clazz); 方法来更新Class文件
        for (Class clazz:inst.getAllLoadedClasses()) {
            log.info("findout class, name = {}", clazz.getName());
//            if (clazz.getName().contains("com.bryant.agent.TestAgentBean")){
//                try {
//                    instrumentation.retransformClasses(clazz);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
        }


    }

}