package com.bryant.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;

@Slf4j
public class Test005 {

    public static void main(String[] args) {
        String s = "ac";
        log.info(longestPalindrome(s));
    }

    public static String longestPalindrome(String s) {
        int l = s.length();
        // a[i][j] -> a[i:j]是否匹配回文
        boolean[][] a = new boolean[l][l];
        if (s.length() == 1 || s.length() == 0) {
            return s;
        }

        // 单字符
        for (int i = 0; i < l; i++) {
            a[i][i] = true;
        }

        int maxLen = 1;
        int start = 0;

        char[] c = s.toCharArray();
        // 字符长度步长
        for (int step = 2; step <= l; step++) {
            // 枚举左边界，左边界的上限设置可以宽松一些
            for (int i = 0; i <l; i++) {
                // 右边界
                int j = step + i -1;
                // 如果右边界越界，就可以退出当前循环
                if (j >= l) {
                    break;
                }

                // 比较字符串字符
                if(c[i] != c[j]) {
                    a[i][j] = false;
                } else {
                    // 相隔一个字符
                    if(j - i <= 2) {
                        a[i][j] = true;
                    } else {
                        // 按之前的最佳方案
                        a[i][j] = a[i+1][j-1];
                    }
                }

                // 设置最大值
                if (a[i][j] && j - i + 1 >maxLen) {
                    maxLen = j - i +1;
                    start = i;
                }
            }
        }
        return s.substring(start, start + maxLen);
    }
}
