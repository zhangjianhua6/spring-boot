package com.jason.web.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: zhangjianhua
 * @date: 2019-12-25 11:35
 **/
@Aspect
public class LogAspect {

    private Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution(* com.jason.springboot.web.controller.*.*( ..))")
    public void addLog(){
        logger.info("我是日志切面处理");
    }

    @Before("addLog()")
    public void before(){
        System.out.println("我是日志前置处理");
    }

}
