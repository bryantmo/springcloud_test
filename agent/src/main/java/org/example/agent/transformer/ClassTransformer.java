package org.example.agent.transformer;

import cn.bigfire.util.FileUtil;
import cn.hutool.core.util.StrUtil;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class ClassTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (className.equals("com/bryant/agent/TestAgentBean")){
            String root = StrUtil.subBefore(System.getProperty("user.dir"), "JavaAgentDemo", true);
            String classFile = root + "JavaAgentDemo/agent/src/main/resources/Console.class";
            return FileUtil.readBytes(classFile);
        }
        return classfileBuffer;
    }
}