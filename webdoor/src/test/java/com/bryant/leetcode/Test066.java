package com.bryant.leetcode;

import java.util.Arrays;

public class Test066 {

    public static void main(String[] args) {

//        int[] digits = new int[]{3,4,6,4,9};
        int[] digits = new int[]{9};
        digits = plusOne(digits);

        Arrays.stream(digits).forEach( d -> {
            System.out.print(d);
        });

    }

    public static int[] plusOne(int[] digits) {

        boolean m = false;
        for (int i = digits.length -1 ; i >= 0; i--) {
            int val = digits[i];

            if (m) {
                if (val + 1 == 10) {
                    digits[i] = 0;
                    m = true;
                    continue;
                } else {
                    digits[i] = digits[i] + 1;
                    m = false;
                    break;
                }
            } else {
                if (val + 1 == 10) {
                    m = true;
                    digits[i] = 0;
                } else {
                    digits[i] = digits[i] + 1;
                    break;
                }
            }
        }

        if (m) {
            int[] newArray = new int[digits.length+1];
            newArray[0] = 1;
            System.arraycopy(digits, 0, newArray, 1, digits.length);
            return newArray;
        }

        return digits;
    }
}
