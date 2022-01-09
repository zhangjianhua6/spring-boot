package com.jason.web.test.sort;

import java.util.Arrays;

public class QuickSort {
    public static void main(String[] args) {
        int[] arr = { 6, 1, 2, 7, 9, 11, 4, 5, 10, 8 };
        System.out.println("原始数据: " + Arrays.toString(arr));
        customQuickSort(arr, 0, arr.length - 1);
        System.out.println("快速排序: " + Arrays.toString(arr));

    }

    public static void customQuickSort(int[] arr, int low, int high){
        int i, j, temp, t;
        if (low >= high){
            return;
        }
        i = low;
        j = high;
        temp = arr[low];

        while (i < j){
            while (temp <= arr[j] && i < j){
                j--;
            }

            while (temp >= arr[i] && i < j){
                i++;
            }

            t = arr[i];
            arr[i] = arr[j];
            arr[j] = t;
        }
        arr[low] = arr[i];
        arr[i] = temp;
        // 递归调用左半数组
        customQuickSort(arr, low, j - 1);
        // 递归调用右半数组
        customQuickSort(arr,  j + 1, high);

    }
}
