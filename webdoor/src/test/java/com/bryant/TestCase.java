package com.bryant;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

public class TestCase {

    private static int ptr = 0;
    private static String res = new String();
    private static int k = 0;

    public static void main(String[] args) {
//        String s = "3[a2[c]]";
//        System.out.println("result = " + decodeString(s));
//        System.out.println("result = " + decodeStringV2(s));

        LinkedList<Integer> integers = new LinkedList<>();
        integers.add(1);
        integers.add(2);
    }

    public static String decodeStringV2(String s) {
        LinkedList<Integer> stack_multi = new LinkedList<>();
        LinkedList<String> stack_res = new LinkedList<>();
        String currentString = ""; // 当前解码字符串
        int k = 0; // 当前的倍数

        for (char ch : s.toCharArray()) {
            if (Character.isDigit(ch)) {
                k = k * 10 + (ch - '0');
            }
            else if (ch == '[') {
                // 入闸
                stack_multi.addLast(k);
                stack_res.addLast(currentString);

                // 中间态的次数和临时缓存
                currentString = "";
                k = 0;
            }
            else if (ch == ']') {
                // 出闸
                Integer count = stack_multi.removeLast();
                StringBuilder tmp = new StringBuilder();
                while (count-- > 0) {
                    tmp.append(currentString);
                }
                currentString = stack_res.removeLast() + tmp;
            }
            else {
                currentString += ch;
            }
        }
        return currentString;
    }
    /**
     * 394. 字符串解码
     * https://leetcode.cn/problems/decode-string/?envType=company&envId=aliyun&favoriteSlug=aliyun-three-months
     * @param s
     * @return
     */
    public static String decodeString(String s) {
        Deque<Integer> countStack = new ArrayDeque<>(); // 存储数字
        Deque<String> stringStack = new ArrayDeque<>(); // 存储字符串
        String currentString = ""; // 当前解码字符串
        int k = 0; // 当前的倍数

        for (char ch : s.toCharArray()) {
            if (Character.isDigit(ch)) {
                k = k * 10 + (ch - '0'); // 处理多位数
            } else if (ch == '[') {
                // 遇到 '['，将当前的字符串和数字推入各自的栈
                countStack.push(k);
                stringStack.push(currentString);
                currentString = ""; // 重置当前字符串
                k = 0; // 重置倍数
            } else if (ch == ']') {
                // 遇到 ']'，解码
                StringBuilder temp = new StringBuilder(stringStack.pop());
                int repeatTimes = countStack.pop();
                for (int i = 0; i < repeatTimes; i++) {
                    temp.append(currentString); // 重复当前字符串
                }
                currentString = temp.toString(); // 更新当前字符串
            } else {
                // 如果是字母，直接加到当前字符串
                currentString += ch;
            }
        }

        return currentString;
    }

    private static String getContent(Integer pop, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < pop; i++) {
            stringBuilder.append(string);
        }
        return stringBuilder.toString();
    }
}
