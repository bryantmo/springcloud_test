package com.bryant.leetcode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test11 {

    public static void main(String[] args) {
//        int[] a = new int[]{1,1};
        int[] a = new int[]{1,8,6,2,5,4,8,3,7};
        log.info("max = {}", maxArea2(a));
    }

    public static int maxArea2(int[] height) {
        int l = 0, r = height.length-1;
        int max = 0;

        while (l < r) {
            int tmp = (height[l] < height[r]? height[l] :height[r]) * (r-l);
            max = tmp>max ? tmp : max;
            if (height[l] > height[r]) {
                r--;
            } else {
                l++;
            }
        }

        return max;
    }

    /**
     * 暴力解法
     * @param height
     * @return
     */
    public static int maxArea(int[] height) {
        int max = 0;
        for(int i = 0; i<height.length-1; i++) {
            int l = height[i];
            int j = i+1;
            while(j<height.length) {
                int r = height[j];
                max = ((j - i) * Math.min(l, r)) > max ? ((j - i) * Math.min(l, r)) : max;
                ++j;
            }
        }
        return max;
    }
}
