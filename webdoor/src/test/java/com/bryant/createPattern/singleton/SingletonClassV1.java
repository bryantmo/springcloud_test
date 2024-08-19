package com.bryant.createPattern.singleton;

public class SingletonClassV1 {

    private static SingletonClassV1 INSTANCE = null;

    private SingletonClassV1() {}

    /**
     * 非线程安全
     * @return
     */
    public static SingletonClassV1 getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new SingletonClassV1();
        }
        return INSTANCE;
    }

}
