package com.bryant.syntax;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

public interface HelloInterface {
//    static {
//        int a = 1;
//    }

    void test();

    void test1();

    default void test2() {

    }

    static void test22() {

    }

    int a= 1;

    public String aa = "111";

    Hashtable h = new Hashtable();

    HashMap h2 = new HashMap();

    ConcurrentHashMap h3 = new ConcurrentHashMap();
}
