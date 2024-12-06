package org.example.agent.transformer;

import cn.bigfire.util.FileUtil;
import cn.hutool.core.util.StrUtil;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * 在JDK 1.6以前，instrument只能在JVM刚启动开始加载类时生效，而在JDK 1.6之后，instrument支持了在运行时对类定义的修改。
 */
public class ClassTransformer implements ClassFileTransformer {

    /**
     * 要使用instrument的类修改功能，我们需要实现它提供的ClassFileTransformer接口，定义一个类文件转换器。
     * 接口中的transform() 方法会在类文件被加载时调用，而在transform方法里，
     * 我们可以利用上文中的ASM 或 Javassist对传入的字节码进行改写或替换，生成新的字节码数组后返回。
     *
     * @param loader                the defining loader of the class to be transformed,
     *                              may be {@code null} if the bootstrap loader
     * @param className             the name of the class in the internal form of fully
     *                              qualified class and interface names as defined in
     *                              <i>The Java Virtual Machine Specification</i>.
     *                              For example, <code>"java/util/List"</code>.
     * @param classBeingRedefined   if this is triggered by a redefine or retransform,
     *                              the class being redefined or retransformed;
     *                              if this is a class load, {@code null}
     * @param protectionDomain      the protection domain of the class being defined or redefined
     * @param classfileBuffer       the input byte buffer in class file format - must not be modified
     *
     * @return
     * @throws IllegalClassFormatException
     */
    @Override
    public byte[] transform(ClassLoader loader, String className,
                            Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {
        if (className.equals("com/bryant/agent/TestAgentBean")){
            String root = StrUtil.subBefore(System.getProperty("user.dir"), "JavaAgentDemo", true);
            String classFile = root + "JavaAgentDemo/agent/src/main/resources/Console.class";
            return FileUtil.readBytes(classFile);
        }
        return classfileBuffer;
    }
}