package com.bryant.leetcode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test075 {

    public static void main(String[] args) {
        int[] a = new int[]{2,3,1,3,2,4,6,7,9,2,19};
        int[] b = new int[]{2,1,4,3,9,6};
        int[] ints = relativeSortArray(a, b);
        log.info("{}", ints);
    }

    /**
     * 计数排序法
     * @param arr1
     * @param arr2
     * @return
     */
    public static int[] relativeSortArray(int[] arr1, int[] arr2) {
        int upper = 0;
        for (int x : arr1) {
            upper = Math.max(upper, x);
        }

        // 计数器 key=a1.value, value=a1.count
        int[] data = new int[upper+1];
        for (int i = 0; i < arr1.length; i++) {
            data[arr1[i]]++;
        }

        //合并有效数据
        int start = 0;
        for (int j = 0; j < arr2.length; j++) {
            while(data[arr2[j]] > 0) {
                arr1[start++] = arr2[j];
                data[arr2[j]]--;
            }
        }

        //合并无效数据
        for(int k = 0; k <data.length; k++) {
            while (data[k] > 0) {
                arr1[start++] = k;
                data[k]--;
            }
        }

        return arr1;
    }

}
