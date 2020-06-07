package com.jason.web.proxy;

import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;

/**
 * @author: zhangjianhua
 * @date: 2019-12-17 17:03
 **/
public class Test {
    public static void main(String[] args) {
        Person meiPo = (Person) new MeiPo().newInstance(new Jason());
        meiPo.findLove();

        byte[] gengarateProxyClass = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{meiPo.getClass()});

        try {
            FileOutputStream fos = new FileOutputStream("D:/$Proxy0.class" );
            fos.write(gengarateProxyClass);
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
