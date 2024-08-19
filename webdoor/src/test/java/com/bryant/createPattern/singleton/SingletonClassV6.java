package com.bryant.createPattern.singleton;

public class SingletonClassV6 {

    enum SingletonEnum {
        INSTANCE;

        //懒加载，创建一个枚举对象，该对象天生为单例
        private SingletonClassV6 singleton;

        //私有化枚举的构造函数（强调不可外部实例化）
        private SingletonEnum() {
            singleton = new SingletonClassV6();
        }

        public static SingletonClassV6 getEnumInstance(){
            return INSTANCE.singleton;
        }
    }

    public static SingletonClassV6 getInstance(){
        return SingletonEnum.getEnumInstance();
    }

}
