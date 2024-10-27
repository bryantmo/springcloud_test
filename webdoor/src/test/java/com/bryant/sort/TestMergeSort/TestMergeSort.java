package com.bryant.sort.TestMergeSort;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * 归并算法
 */
@Slf4j
public class TestMergeSort {

    private static int[] a = new int[]{8,7,6,5,3,2,0};

    public static void main(String[] args) {
        if (a == null || a.length == 0)
            return;
        int[] tmp = new int[a.length];
        doDivide(a, 0, a.length-1, tmp);
        log.info(Arrays.toString(a));
    }

    private static void doDivide(int[] a, int head, int tail, int[] tmp) {
        if (head < tail) {
            // 二分法取中值
            int mid = (head + tail) / 2;
            doDivide(a, head, mid, tmp);
            doDivide(a, mid + 1, tail, tmp);
            doMerge(a, head, mid, tail, tmp);
        }
    }

    private static void doMerge(int[] a, int head, int mid, int tail, int[] tmp) {
        int i = head, j = mid+1; //一个启动、另一个起点
        int m = mid, n = tail;//一个终点、另一个终点
        int k = 0;

        // 两个数组还没遍历完（各个数组的起始指针<各个数组的末尾指针）
        while (i <= m && j <= n) {
            if (a[i] < a[j]) {
                tmp[k++] = a[i++];
            } else {
                tmp[k++] = a[j++];
            }
        }

        // 说明j已经遍历到末尾n了，i还没有遍历到底（注意边界问题，=号）
        while(i <= m) {
            tmp[k++] = a[i++];
        }

        // 说明j已经遍历到末尾n了，i还没有遍历到底（注意边界问题，=号）
        while(j <= n) {
            tmp[k++] = a[j++];
        }


        // 排好顺序的tmp，回填到原始数组
        for (int l = 0; l < k; l++) {
            a[head + l] = tmp[l];
        }

    }
}
