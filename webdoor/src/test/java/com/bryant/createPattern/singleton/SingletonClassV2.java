package com.bryant.createPattern.singleton;

public class SingletonClassV2 {

    //类初始化时，就已经创建对象，因此线程安全
    private static SingletonClassV2 INSTANCE = new SingletonClassV2();

    private SingletonClassV2() {}

    /**
     * 非线程安全
     * @return
     */
    public static SingletonClassV2 getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new SingletonClassV2();
        }
        return INSTANCE;
    }

}
