package com.bryant;

public class Test088 {

    public static void main(String[] args) {
        int[] nums1 = new int[]{1,2,3,0,0,0};
        int m = 3;
        int[] nums2 = new int[]{2,5,6};
        int n = 3;
        merge(nums1, m, nums2, n);
        System.out.println(nums1);
    }

    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int i=m+n-1;
        m--;
        n--;
        for(; i >= 0; i--) {
            if(n < 0) {
                nums1[i] = nums1[m];
                m--;
                continue;
            } else if(m < 0) {
                nums1[i] = nums2[n];
                n--;
                continue;
            }

            if(nums1[m] > nums2[n]) {
                nums1[i] = nums1[m];
                m--;
            } else {
                nums1[i] = nums2[n];
                n--;
            }

        }
    }
}