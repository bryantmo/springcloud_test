package com.bryant;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.TreeMap;

public class RedisTest {
    public static void main(String[] args) {
        A a = new A();
        a.setA(1);
        List<A> as = Arrays.asList(a);
        for (A a1 : as) {
            System.out.println(a1.getA());
        }

        a.setA(2);
        for (A a1 : as) {
            System.out.println(a1.getA());
        }
    }

    private static void test(int a , int b)  {

    }
}

class A{
    private int a;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }
}