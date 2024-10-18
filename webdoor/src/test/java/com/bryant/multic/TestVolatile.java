package com.bryant.multic;

public class TestVolatile {

    private static Integer num = 110;

    public static void main(String[] args) {
        System.out.println(num);
        num = 1111;
    }
}
