package com.bryant.createPattern.singleton;

public class SingletonClassV4 {

    private static SingletonClassV4 INSTANCE = null;

    private SingletonClassV4() {}

    /**
     * JVM的指令重排序，可能导致并发下的重复创建
     * @return
     */
    public static SingletonClassV4 getInstance(){
        if (INSTANCE == null) {
            synchronized (SingletonClassV4.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SingletonClassV4();
                }
                return INSTANCE;

            }
        }
        return INSTANCE;
    }

}
