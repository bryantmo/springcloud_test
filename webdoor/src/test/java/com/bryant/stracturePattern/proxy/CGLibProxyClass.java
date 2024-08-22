package com.bryant.stracturePattern.proxy;

import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodProxy;

import javax.security.auth.callback.Callback;
import java.lang.reflect.Method;

public class CGLibProxyClass {
    public static void main(String[] args) {
        //可以指定 CGLIB 将动态生成的代理类保存至指定的磁盘路径下
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,"E:\\code\\test\\src\\main\\java\\design_module\\ProxyPattern\\dynamicProxy2");

        Enhancer enhancer=new Enhancer();
        enhancer.setSuperclass(SubjectA.class);
//        enhancer.setCallback(new CGlibMyMethodInterceptor());
        //生成代理类
        SubjectA ud=(SubjectA) enhancer.create();
        ud.find();
        enhancer.setSuperclass(SubjectB.class);
        SubjectB ud2=(SubjectB) enhancer.create();
        ud2.find();
    }
}


interface MethodInterceptor extends Callback
{
    public Object intercept(Object obj, java.lang.reflect.Method method, Object[] args,
                            MethodProxy proxy) throws Throwable;
}

/**
 * <p>
 *     CGlib 动态代理拦截器类,实现  MethodInterceptor 接口
 * </p>
 */
class CGlibMyMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable
    {
        System.out.println("开始CGLib动态代理");
        Object object=proxy.invokeSuper(obj, args);
        System.out.println("结束CGLib动态代理");
        return object;
    }
}


/**
 * <p>
 *     可以看出使用Enhancer生成代理类，需要设置被代理类，也就是父类（从这里可以看出是使用继承，生成的子类），
 *     设置回调方法
 * </p>
 */
class SubjectA {
    void find(){
        System.out.println("SubjectA");
    }
}
class SubjectB {
    void find(){
        System.out.println("SubjectB");
    }
}