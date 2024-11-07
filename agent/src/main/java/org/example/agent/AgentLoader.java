package org.example.agent;

import lombok.extern.slf4j.Slf4j;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * AgentLoader暂时没有使用到
 */
@Slf4j
public class AgentLoader {
    public static void premain(String agentArgs, Instrumentation inst) {
        log.info("AgentLoader start, time = {}", System.currentTimeMillis());
        inst.addTransformer(new MyAgent());
        inst.addTransformer(new Agent1());
        try {
            inst.retransformClasses(AgentLoader.class);
        } catch (UnmodifiableClassException e) {
            e.printStackTrace();
        }
    }
}
