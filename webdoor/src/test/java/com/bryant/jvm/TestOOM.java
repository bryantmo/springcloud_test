package com.bryant.jvm;

import java.util.ArrayList;

public class TestOOM {

    public static void main(String[] args) throws InterruptedException {
        testJSTAT();
    }

    private static void testJSTAT() throws InterruptedException {
        ArrayList<Integer> objects = new ArrayList<>();
        ArrayList<Integer> objects2 = new ArrayList<>();
        for (int i = 0; i < 10000000; i++) {
            Integer integer = new Integer(i);
            objects.add(integer);
            System.out.println("sleeping.. i = " + i);
            objects2.addAll(objects);
            if (i > 830) {
                Thread.sleep(10);
            }
        }
    }
}
