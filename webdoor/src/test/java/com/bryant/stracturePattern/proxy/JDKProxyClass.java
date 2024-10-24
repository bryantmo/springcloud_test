package com.bryant.stracturePattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK - 方法
 * CGlib - 对象ASM框架
 */
public class JDKProxyClass {
    public static void main(String[] args) {
        //被代理类
        User1Dao ud=new User1Dao();
        Woman1Dao wd=new Woman1Dao();

        //代理器（通过构造器设置 被代理类）
        JDKDynamicProxy dp = new JDKDynamicProxy(ud);
        JDKDynamicProxy dp2 = new JDKDynamicProxy(wd);

        //在创建代理对象前，可以通过下面语句，将生成的代理类的.class保存在本地。
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        //生成代理对象1
        IUserDao iud = (IUserDao) Proxy.newProxyInstance(ud.getClass().getClassLoader(), ud.getClass().getInterfaces(), dp);
        iud.find();
        iud.abandon();

        //切换代理对象2，生成
//        iud = (IUserDao) Proxy.newProxyInstance(wd.getClass().getClassLoader(), wd.getClass().getInterfaces(), dp2);
//        iud.find();
    }
}

/**
 * <p>公共接口</p>
 */
interface IUserDao {
    void find();
    void abandon();
}
/**
 * 被代理类或者叫代理目标类
 */
class User1Dao implements IUserDao{
    @Override
    public void find() {
        System.out.println("查找用户");
    }

    @Override
    public void abandon() {
        System.out.println("丢弃信息");
    }
}

class Woman1Dao implements IUserDao {
    @Override
    public void find() {
        System.out.println("查找女性用户");
    }
    @Override
    public void abandon() {
        System.out.println("丢弃女拳信息");
    }
}

/**
 * <p>
 *     JDK 动态代理拦截器类
 *
 *     这里解耦了被代理对象
 * </p>
 */
class JDKDynamicProxy implements InvocationHandler {

    //被代理类的实例
    private IUserDao iud;

    //构造方法
    public JDKDynamicProxy(IUserDao iud){
        this.iud=iud;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result=null;
        System.out.println("开始JDK动态代理");
        method.invoke(iud, args);
        System.out.println("结束JDK动态代理");
        return result;
    }
}