package com.bryant.sort;

import lombok.extern.slf4j.Slf4j;

import java.util.stream.Stream;

/**
 * 选择排序法
 */
@Slf4j
public class SimpleSelectTest {

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
        for (int i = 0; i < a.length; i++) {

            int tmp = i;
            for (int j = tmp; j < a.length; j++) {
                if (a[tmp] > a[j]) {
                    tmp = j;
                }
            }

            // 如果tmp>i，替换a[i]
            if (tmp > i) {
                int v = a[i];
                a[i] = a[tmp];
                a[tmp] = v;
            }
        }
        return a;
    }
}
