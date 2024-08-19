package com.bryant.createPattern.singleton;

public class SingletonClassV5 {
    private static class SingleTonHoler{
        private static SingletonClassV5 INSTANCE = new SingletonClassV5();
    }

    /**
     * 私有化构造器
     */
    private SingletonClassV5() {}

    /**
     * 获取单例方法，getInstance()获取单例的方法，不会触发多次new操作，所以只会返回同一个对象
     * @return
     */
    public static SingletonClassV5 getInstance() {
        return SingleTonHoler.INSTANCE;
    }

    /**
     * 在SingletonClass内部，封装的一个静态内部类
     *
     * 静态内部类的优点是：
     * - 懒加载：外部类加载时并不需要立即加载内部类，内部类不被加载则不去初始化INSTANCE，故而不占内存。
     *       即当SingleTon第一次被加载时，并不需要去加载SingleTonHoler，只有当getInstance()方法第一次被调用时，才会去初始化INSTANCE，
     *       第一次调用getInstance()方法会导致虚拟机加载SingleTonHoler类，
     *
     *      JVM有5个主动引用而类加载的场景，而静态内部类是被动引用，所以不会触发提前加载。
     *      - 类加载时机：JAVA虚拟机在有且仅有的5种场景下会对类进行初始化。
     *
     *          1.遇到new、getstatic、setstatic或者invokestatic这4个字节码指令时，对应的java代码场景为：new一个关键字或者一个实例化对象时、读取或设置一个静态字段时(final修饰、已在编译期把结果放入常量池的除外)、调用一个类的静态方法时。
     *
     *
     *
     *          2.使用java.lang.reflect包的方法对类进行反射调用的时候，如果类没进行初始化，需要先调用其初始化方法进行初始化。
     *
     *
     *
     *          3.当初始化一个类时，如果其父类还未进行初始化，会先触发其父类的初始化。
     *
     *
     *
     *          4.当虚拟机启动时，用户需要指定一个要执行的主类(包含main()方法的类)，虚拟机会先初始化这个类。
     *
     *
     *
     *          5.当使用JDK 1.7等动态语言支持时，如果一个java.lang.invoke.MethodHandle实例最后的解析结果REF_getStatic、REF_putStatic、REF_invokeStatic的方法句柄，并且这个方法句柄所对应的类没有进行过初始化，则需要先触发其初始化。
     *
     *
     *  而 静态内部类并不在5种情况之内，所以静态内部类，是绝对是用到了才会加载的资源。
     *  因此，这种方法不仅能确保线程安全，也能保证单例的唯一性，同时也延迟了单例的实例化。
     */


    public static void main(String[] args) {
        SingletonClassV5 instance = SingletonClassV5.getInstance();
        System.out.println(instance.hashCode());
    }


}
