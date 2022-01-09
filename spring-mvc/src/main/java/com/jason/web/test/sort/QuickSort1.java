package com.jason.web.test.sort;

import java.util.Arrays;

public class QuickSort1 {
    public static void main(String[] args) {
        int[] arr = {20,13,6,23,8,46,33,11,17,92,34,78};
        System.err.println("原始数组：" + Arrays.toString(arr));
        sort(arr, 0, arr.length - 1);
        System.err.println("排序数组：" + Arrays.toString(arr));
    }

    public static void sort(int[] arr, int low, int high){
        if (low >= high){
            return;
        }

        int temp = arr[low];
        int i = low;
        int j = high;

        while (i<j){
            while (i<j && temp <= arr[j]){
                j--;
            }
            arr[i] = arr[j];
            while (i<j && temp >= arr[i]){
                i++;
            }
            arr[j] = arr[i];
        }
        arr[i] = temp;
        sort(arr, low, i-1);
        sort(arr, i+1, high);
    }
}
