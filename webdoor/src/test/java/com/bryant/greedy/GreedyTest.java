package com.bryant.greedy;

import lombok.extern.slf4j.Slf4j;

/**
 * N个苹果，用6号袋和8号袋装，分别能装6个和8个苹果
 * 必须装满每个袋子，最少需要多少个袋子才能装满？
 * 无法满足要求的话，返回-1
 */
@Slf4j
public class GreedyTest {
    public static void main(String[] args) {
        int n = 12;
        log.info("result = {}", greedy(n));
    }

    public static int greedy(int n) {
        // 最大8个果子的袋子数量
        int c6 = -1;
        int c8 = -1;
        c8 = n / 8;
        //余数
        int m = n - c8 * 8;
        while ( c8 >= 0 && m >= 0) {
            if (m % 6 == 0) {
                // 说明够袋子装了，可以退出
                c6 = m / 6;
                break;
            } else {
                // 让最大袋子数量-1，继续动态计算
                c8--;
                m = n - c8 * 8;
            }
        }


        return c6 > -1 ? (c6+c8): -1;
    }
}
