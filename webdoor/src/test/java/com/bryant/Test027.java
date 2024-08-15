package com.bryant;

import java.util.Arrays;

public class Test027 {
    public static void main(String[] args) {
        int[] list = new int[]{3,2,2,3};
        int val = 3;
        System.out.println(removeElement(list, val));
    }

    public static int removeElement(int[] nums, int val) {
        int[] nums2 = new int[nums.length];
        int i = 0, j = 0;
        for(int num : nums) {
            if(num == val) {
                continue;
            } else {
                nums2[j++] = num;
            }
        }
        nums = Arrays.copyOf(nums2, j-1);
        return j;
    }
}
