package com.jason.web.test;

import com.jason.web.pojo.response.AopResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: zhangjianhua
 * @date: 2019-12-18 09:55
 **/
public class IntegerTest {

    public static void main(String[] args) {
        /*for (int i = 0; i < 150; i++) {
            Integer a = i;
            Integer b = i;
            System.err.println(i + " " + (a - b == 0));

            //System.out.println(a+" "+b+" "+System.identityHashCode(a)+" "+System.identityHashCode(b));

        }*/
        Integer num1 = 100;
        Integer num2 = 200;
        Long num3 = 300l;
        System.out.println(num3 == (num1 + num2)); //tr
        final AopResponse response = new AopResponse();
        System.err.println(response.getCode());
        response.setCode("000");
        System.err.println(response.getCode());

        System.err.println(getString());

    }

    public static String getString(){
        try {
            return "123";
        }finally {
            return "456";
        }
    }
}
