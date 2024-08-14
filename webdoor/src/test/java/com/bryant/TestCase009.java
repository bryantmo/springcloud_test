package com.bryant;

public class TestCase009 {

    public static void main(String[] args) {
        int x = 124421;
        System.out.println(isPalindrome(x));
    }

    public static boolean isPalindrome(int x) {
        int tmp = x;
        int y = 0;
        while (tmp > 0) {
            int k = tmp % 10;
            y = y * 10 + k;
            tmp = tmp / 10;
        }
        return y == x;
    }
}
