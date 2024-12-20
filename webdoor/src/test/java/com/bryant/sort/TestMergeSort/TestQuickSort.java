package com.bryant.sort.TestMergeSort;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class TestQuickSort {

    private static int[] a = new int[]{8,7,6,5,3,2,0};

    public static void main(String[] args) {
        if (a == null || a.length == 0)
            return;
        quickSort(a, 0, a.length-1);

        log.info(Arrays.toString(a));
    }

    /**
     * 递归快速排序
     * @param a
     * @param low
     * @param high
     */
    private static void quickSort(int[] a, int low, int high) {
        if (low >= high) {
            return;
        }
        // 选出基准值
        int p = partition(a, low, high);
        // 递归排序
        quickSort(a, low, p - 1);
        quickSort(a, p+1, high);
    }

    private static int partition(int[] a, int low, int high) {
        int pivot = a[low];
        while(low < high) {
            while (low<high && a[high] > pivot) {
                high--;
            }
            swap(a, low, high);

            while(low < high && a[low] <pivot) {
                low++;
            }
            swap(a, low
            , high);
        }
        return low;
    }

    private static void swap(int[] a, int low, int high) {
        int tmp = a[low];
        a[low] = a[high];
        a[high] = tmp;
    }

}
