package com.jason.web.test;

import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * @author zhangjianhua
 * @date 2021/3/19 18:42
 */
public class ThreadStateTest {

    private  Object lock = new Object();

    private volatile int count = 0;

    public void product() {
        synchronized(lock){
            System.err.println("生产一个美女");
            count++;
            if (count <= 1){
                lock.notify();
            }
        }

    }

    public  void consumer() {
        synchronized(lock){
            if (count <= 0 ){
                System.err.println("未发现一个美女");
                try {
                    lock.wait();
                } catch(InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
            System.err.println("消费一个美女");
        }

    }

    public static void main(String[] s) {
        ThreadStateTest test = new ThreadStateTest();

        new Thread(new Runnable() {
            @Override
            public void run() {
                test.consumer();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                test.product();
            }
        }).start();
    }

}
