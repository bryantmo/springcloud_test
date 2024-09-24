package com.bryant.reference;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class TestReference {

    public static void main(String[] args) {

//        testStrongReference();

//        softRefenceTest();
        testWeakReference();
    }

    /**
     * 强引用：当对象指向内存为空不关联时，才会被GC回收，否则永远不会回收。
     */
    private static void testStrongReference() {
        String str = "StrongReference";
        System.out.println(str);
        System.gc();
        System.out.println(str);
        str = null;
        System.out.println(str);
    }

    /**
     *  仅仅gc， 还不会回收。需内存不够 GC的时候才回收软引用。
     * 启动参数：
     * -Xmx16m  -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:NewSize=2m -XX:MaxNewSize=2m
     */
    private static void softRefenceTest() {
        final int _8M = 8 * 1024 * 1024;
        List<SoftReference> list = new ArrayList<>();
        System.out.println("add 8m  -1");
        list.add(new SoftReference(new byte[_8M]));
        System.out.println("add 8m  -2");
        list.add(new SoftReference(new byte[_8M]));
        System.out.println("add 8m  -3");
        list.add(new SoftReference(new byte[_8M]));
        System.out.println("add 8m  -4");
        list.add(new SoftReference(new byte[_8M]));
        System.gc();
        System.out.println("add 8m  -5");
        list.add(new SoftReference(new byte[_8M]));
        System.out.println("add 8m  -6");
        list.add(new SoftReference(new byte[_8M]));
        System.gc();
        list.stream().forEach(r-> System.out.println(r.get()));
    }


    /**
     * <p>
     *     弱引用：无论内存是否足够，都会被GC回收
     * </p>
     */

    public static void testWeakReference() {
        WeakReference weakReference = new WeakReference(new long[1024*1024]);
        System.out.println(weakReference.get());
        System.gc();
        System.out.println(weakReference.get());
    }

}
