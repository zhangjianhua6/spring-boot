package com.jason.web.test;

/**
 * 找出数组中出现次数超过数组长度一半的元素
 */
public class FindTheElement {
    public static void main(String[] args) {
        int[] a = {1,2,2,1,1,2,2,1,1,2,2,1,5,5,5};
        int result = a[0];
        int times = 1;
        for (int i = 1; i < a.length; i++) {
            if (a[i] != result) {
                times--;
            }
            else {
                times++;
            }
            if (times == -1) {
                times = 1;
                result = a[i];
            }
        }
        System.out.println(result);
    }
}
