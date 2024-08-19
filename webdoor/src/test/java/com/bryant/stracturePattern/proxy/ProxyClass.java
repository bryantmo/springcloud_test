package com.bryant.stracturePattern.proxy;

public class ProxyClass {
    public static void main(String[] args){
        //代理类
        ProxyHuman proxyHuman = new ProxyHuman(new UserDao());
        proxyHuman.visit();
        //代理类
        proxyHuman.setSubject(new WomanDao());
        proxyHuman.visit();
    }
}

/**
 * <p>
 *     代理类
 * </p>
 */
class ProxyHuman {
    private Subject subject;
    public ProxyHuman(Subject subject) {
        this.subject = subject;
    }
    public void visit() {
        subject.visit();
    }
    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}

interface Subject {
    void visit();
}
class UserDao implements Subject {
    private String name = "name-1";
    @Override
    public void visit() {
        System.out.println("my name is "+name);
    }
}
class WomanDao implements Subject {
    private String name = "name-2";
    @Override
    public void visit() {
        System.out.println("my name is "+name);
    }
}