package org.example.agent;

import lombok.extern.slf4j.Slf4j;
import org.example.agent.transformer.ClassTransformer;

import java.lang.instrument.Instrumentation;

@Slf4j
public class JvmMonitorAgentMainAgent {

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
