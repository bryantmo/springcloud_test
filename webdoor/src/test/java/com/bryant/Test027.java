package com.bryant;

public class Test027 {
    public static void main(String[] args) {
        int[] list = new int[]{3,2,2,3};
        int val = 3;
        System.out.println(removeElement(list, val));
    }

    /**
     * 双指针算法-双指针：同时从左端起步，一个负责遍历，一个负责填数据
     * @param nums
     * @param val
     * @return
     */
    public static int removeElement(int[] nums, int val) {
        int i = 0;
        int j = 0;
        for(; j < nums.length; j++) {
            if(val != nums[j]){
                nums[i++] = nums[j];
            }
        }
        return i;
    }
}
