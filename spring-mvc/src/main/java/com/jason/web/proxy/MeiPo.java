package com.jason.web.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: zhangjianhua
 * @date: 2019-12-17 16:39
 **/
public class MeiPo implements InvocationHandler {

    private Person person;

    public Object newInstance(Person person) {
        this.person = person;
        return Proxy.newProxyInstance(person.getClass().getClassLoader(), person.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("媒婆有大把资源，说出你想要的");
        method.invoke(person, args);
        System.out.println("媒婆把联系方式给你，你可以谈恋爱了");
        return  null;
    }
}
