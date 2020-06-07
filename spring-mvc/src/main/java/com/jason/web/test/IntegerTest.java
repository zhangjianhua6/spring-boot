package com.jason.web.test;

/**
 * @author: zhangjianhua
 * @date: 2019-12-18 09:55
 **/
public class IntegerTest {

    public static void main(String[] args) {
        for (int i = 0; i < 150; i++) {
            Integer a = i;
            Integer b = i;
            System.err.println(i + " " + (a == b));
            //System.out.println(a+" "+b+" "+System.identityHashCode(a)+" "+System.identityHashCode(b));

        }
    }
}
