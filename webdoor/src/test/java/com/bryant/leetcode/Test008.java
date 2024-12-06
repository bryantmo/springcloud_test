package com.bryant.leetcode;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test008 {
    public static void main(String[] args) {
//        String s = "1337c0d3";
//        String s = "words and 987";
//        String s = "0-1";
//        String s = "   -042";
//        String s = "+-12";
//        String s = "42";
//        String s = "";
//        String s = "21474836460"; //预期结果：2147483647
//        String s = "  -0012a42";
//        String s = "-91283472332";
        String s = "-2147483647";
        log.info("data : {}", myAtoi(s));
        log.info("data : {}", myAtoi2(s));
    }
    public static int myAtoi(String s) {
        // 个位-符号（0-正，1-负数）
        int flag = 1;


        int i = 0;
        // 移除空格
        for(; i<s.length() && s.charAt(i) == ' '; ) {
            i++;
        }

        if (i == s.length()) {
            return 0;
        }

        if (s.charAt(i) == '+') {
            flag = 1;
            i++;
        } else if (s.charAt(i) == '-') {
            flag = -1;
            i++;
        }

        int tmp = 0;
        while(i < s.length()) {

            char ch = s.charAt(i);

            if (ch < '0' || ch > '9') {
                break;
            }

            if(tmp > Integer.MAX_VALUE/10 || (tmp == Integer.MAX_VALUE/10 && (ch - '0') > Integer.MAX_VALUE % 10)) {
                return Integer.MAX_VALUE;
            }

            if(tmp < Integer.MIN_VALUE/10 || (tmp == Integer.MIN_VALUE/10 &&  (ch - '0') > -(Integer.MIN_VALUE % 10))) {
                return Integer.MIN_VALUE;
            }

            tmp = tmp * 10 + flag * (ch - '0');
            i++;
        }
        return tmp;
    }


    public static int myAtoi2(String str) {
        int len = str.length();
        // str.charAt(i) 方法回去检查下标的合法性，一般先转换成字符数组
        char[] charArray = str.toCharArray();

        // 1、去除前导空格
        int index = 0;
        while (index < len && charArray[index] == ' ') {
            index++;
        }

        // 2、如果已经遍历完成（针对极端用例 "      "）
        if (index == len) {
            return 0;
        }

        // 3、如果出现符号字符，仅第 1 个有效，并记录正负
        int sign = 1;
        char firstChar = charArray[index];
        if (firstChar == '+') {
            index++;
        } else if (firstChar == '-') {
            index++;
            sign = -1;
        }

        // 4、将后续出现的数字字符进行转换
        // 不能使用 long 类型，这是题目说的
        int res = 0;
        while (index < len) {
            char currChar = charArray[index];
            // 4.1 先判断不合法的情况
            if (currChar > '9' || currChar < '0') {
                break;
            }

            // 题目中说：环境只能存储 32 位大小的有符号整数，因此，需要提前判：断乘以 10 以后是否越界
            if (res > Integer.MAX_VALUE / 10 || (res == Integer.MAX_VALUE / 10 && (currChar - '0') > Integer.MAX_VALUE % 10)) {
                return Integer.MAX_VALUE;
            }
            if (res < Integer.MIN_VALUE / 10 || (res == Integer.MIN_VALUE / 10 && (currChar - '0') > -(Integer.MIN_VALUE % 10))) {
                return Integer.MIN_VALUE;
            }

            // 4.2 合法的情况下，才考虑转换，每一步都把符号位乘进去
            res = res * 10 + sign * (currChar - '0');
            index++;
        }
        return res;
    }
}
