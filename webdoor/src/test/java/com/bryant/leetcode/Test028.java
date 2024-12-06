package com.bryant.leetcode;

/**
 * Knuth-Morris-Pratt 算法
 * KMP 解法
 * KMP 算法是一个快速查找匹配串的算法，它的作用其实就是本题问题：如何快速在「原字符串」中找到「匹配字符串」。
 *
 * 上述的朴素解法，不考虑剪枝的话复杂度是 O(m∗n) 的，而 KMP 算法的复杂度为 O(m+n)。
 *
 * KMP 之所以能够在 O(m+n) 复杂度内完成查找，是因为其能在「非完全匹配」的过程中提取到有效信息进行复用，以减少「重复匹配」的消耗。
 *
 */
public class Test028 {

    public static void main(String[] args) {
        String haystack = "mississippi";
        String needle = "issip";
        System.out.println(strStr(haystack, needle));
    }

    public static int strStr(String haystack, String needle) {
        if (haystack.length() < needle.length()) {
            return -1;
        }
        char f1 = needle.charAt(0);
        for(int i = 0; i + needle.length() < haystack.length(); i++) {
            if (haystack.charAt(i) == f1) {
                int tmp = i;
                int match = 0;
                for(int j = 0; j < needle.length();) {
                    if (haystack.charAt(tmp++) == needle.charAt(j++)) {
                        match++;
                        continue;
                    }
                    break;
                }
                if (match == needle.length()) {
                    return i;
                }
            }
        }
        return -1;
    }
}
