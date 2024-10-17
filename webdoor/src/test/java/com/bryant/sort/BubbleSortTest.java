package com.bryant.sort;

import lombok.extern.slf4j.Slf4j;

import java.util.stream.Stream;

/**
 * 冒泡算法
 */
@Slf4j
public class BubbleSortTest {
    public static void main(String[] args) {
        int[] a = new int[]{5,3,6,7,2,1};
        a = sort(a);
        Stream.of(a).forEach(
                aa-> {
                    log.info("a = {}", aa);
                }
        );
    }

    public static int[] sort(int a[]) {

        for (int i = 0; i < a.length; i++) {

            for (int j = 0; j < a.length - i - 1; j++) {

                if (a[j] > a[j+1]) {
                    int tmp = a[j+1];
                    a[j+1] = a[j];
                    a[j] = tmp;
                }

            }

        }

        return a;

    }
}
