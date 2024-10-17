package com.bryant.sort;

import lombok.extern.slf4j.Slf4j;

import java.util.stream.Stream;

/**
 * 双指针：
 * i - 待插入序列
 * j - 已有序列
 */
@Slf4j
public class SimpleInsertTest {

    public static void main(String[] args) {
        int[] a = new int[]{5,3,6,7,2,1};
        a = sort(a);
        Stream.of(a).forEach(
                aa-> {
                    log.info("a = {}", aa);
                }
        );
    }


    public static int[] sort(int[] a) {
        for (int i = 1; i < a.length; i++) {
            //子序列
            for (int j = i; j > 0; j--) {
                if (a[j] < a[j-1]) {
                    int tmp = a[j-1];
                    a[j-1] = a[j];
                    a[j] = tmp;
                }
            }
        }
        return a;
    }

}
