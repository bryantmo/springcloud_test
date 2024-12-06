package com.bryant.leetcode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test007 {
    public static void main(String[] args) {
//        int x = 321;
        int x = 1534236469;
        log.info("x = {}", reverse(x));
    }

    public static int reverse(int x) {
        boolean flag = false;
        if (x < 0) {
            x = -x;
            flag = true;
        }
        long r = 0;
        while(x > 0) {
            int a = x % 10;
            x = x/10;
            r = r * 10 + a;
        }

        if (r>Integer.MAX_VALUE)
            r = 0;

        if (flag)
            r = -r;
        return (int) r;
    }
}
