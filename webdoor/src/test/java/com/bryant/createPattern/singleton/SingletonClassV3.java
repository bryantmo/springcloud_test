package com.bryant.createPattern.singleton;

public class SingletonClassV3 {

    //类初始化时，就已经创建对象，因此线程安全
    private static SingletonClassV3 INSTANCE = null;

    private SingletonClassV3() {}

    /**
     * 非线程安全
     * @return
     */
    public static synchronized SingletonClassV3 getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new SingletonClassV3();
        }
        return INSTANCE;
    }

}
