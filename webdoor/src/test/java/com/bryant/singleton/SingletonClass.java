package com.bryant.singleton;

public class SingletonClass {

    /**
     * 私有化构造器
     */
    private SingletonClass() {}

    /**
     * 获取单例方法
     * @return
     */
    public static SingletonClass getInstance() {
        return SingleTonHoler.INSTANCE;
    }

    /**
     * 在SingletonClass内部，封装的一个静态内部类
     *
     * 静态内部类的优点是：
     * - 懒加载：外部类加载时并不需要立即加载内部类，内部类不被加载则不去初始化INSTANCE，故而不占内存。
     *      JVM有5个主动引用而类加载的场景，而静态内部类是被动引用，所以不会触发提前加载。
     *  即当SingleTon第一次被加载时，并不需要去加载SingleTonHoler，只有当getInstance()方法第一次被调用时，才会去初始化INSTANCE，
     *  第一次调用getInstance()方法会导致虚拟机加载SingleTonHoler类，
     *  这种方法不仅能确保线程安全，也能保证单例的唯一性，同时也延迟了单例的实例化。
     * - getInstance()获取单例的方法，不会触发多次new操作，所以只会返回同一个对象
     */
    private static class SingleTonHoler{
        private static SingletonClass INSTANCE = new SingletonClass();
    }

    public static void main(String[] args) {
        SingletonClass instance = SingletonClass.getInstance();
        System.out.println(instance.hashCode());
    }




}
