package com.bryant.dynamic;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestDynamic {

    public static void main(String[] args) {
         int[] a = new int[]{4,6,7,9};
         log.info("max:" + dp_opt(a));
         log.info("max:" + dynamic_process(a));
    }


    private static int dynamic_process(int[] arr) {

        int[] na = new int[arr.length];
        // 前1个元素方案
        na[0] = arr[0];

        //【1】 前2个元素方案：要么取下标=0，要么取下标=1
        na[1] = Math.max(arr[0], arr[1]);

        for (int i = 2; i < arr.length; i++) {
            // 【2】当前值 + 隔一个的最优解
            int a = na[i-2] + arr[i];
            // 前一个最优解
            int b = na[i-1];
            na[i] = Math.max(a, b);
        }

        return na[arr.length-1];
    }

    public static int dp_opt(int[] arr) {
        int[] opt = new int[arr.length];
        opt[0] = arr[0];
        opt[1] = Math.max(arr[0], arr[1]);
        for(int i=2; i<arr.length; i++) {
            int a = opt[i-2] + arr[i];
            int b = opt[i-1];
            opt[i] = Math.max(a, b);
        }
        return opt[arr.length-1];
    }

}
