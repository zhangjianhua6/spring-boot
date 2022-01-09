package com.jason.web.test.sort;

/**
 * 有序数组旋转一次后，查找一个数是否存在
 */
public class Bs {

    public static void main(String[] args) {
        int[] arr = {4,5,1,2,3};
        int[] arr1 = {5,1};
        //System.err.println(bs1(arr, 5, 0, arr.length - 1));
        System.err.println(bs(arr, 5, 0, arr.length - 1));
    }

    public static int bs1(int[] arr, int target, int begin, int end){

        int middle = begin;
        while (begin <= end){
            middle = (begin + end) / 2;
            if (target == arr[middle]){
                return middle;
            }
            if (arr[begin] <= arr[middle]){
                if(arr[begin] <= target && target<= arr[middle]){
                    end = middle - 1;
                }else {
                    begin = middle + 1;
                }
            }else {
                if(arr[middle] <= target && target<= arr[end]){
                    begin = middle + 1;
                }else {
                    end = middle - 1;
                }
            }
        }
        return -1;
    }


    public static int bs(int[] arr, int target, int begin, int end){

        if (begin >= end){
            if (begin == end && target == arr[begin]){
                return begin;
            }else {
                return -1;
            }
        }

        int middle = (begin + end) / 2;
        if (target == arr[middle]){
            return middle;
        }
        if (arr[begin] <= arr[middle]){
            if(arr[begin] <= target && target<= arr[middle]){
                return bs(arr, target, begin, middle - 1);
            }else {
                return bs(arr, target, middle + 1, end);
            }
        }else {
            if(arr[middle] <= target && target<= arr[end]){
                return bs(arr, target, middle + 1, end);
            }else {
                return bs(arr, target, begin, middle - 1);
            }
        }
    }
}
