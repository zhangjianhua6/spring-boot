package com.jason.web.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author: zhangjianhua
 * @date: 2019-12-25 11:34
 **/
@Configuration
@EnableAspectJAutoProxy
public class SpringAopConfig {

    @Bean
    public LogAspect create(){
        return  new LogAspect();
    }

}
