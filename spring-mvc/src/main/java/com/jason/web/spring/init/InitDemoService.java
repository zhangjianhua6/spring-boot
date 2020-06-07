package com.jason.web.spring.init;

import com.jason.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author: zhangjianhua
 * @date: 2019-12-30 14:18
 **/
@Service
@Order(Integer.MAX_VALUE)
public class InitDemoService implements InitializingBean, DisposableBean, ApplicationContextAware, BeanNameAware, BeanFactoryAware {

    private static final Logger logger = LoggerFactory.getLogger(InitDemoService.class);

    private ApplicationContext applicationContext;

    private BeanFactory beanFactory;

    private String beanName;

    private UserService userService;

    public InitDemoService() {
        logger.info("【构造函数】调用构造函数，实例化InitDemoService对象");
    }

    @PostConstruct
    public void postConstructMethod(){
        logger.info("【PostConstruct注解】调用InitDemoService.postConstructMethod()");
        this.userService = applicationContext.getBean(UserService.class);
        logger.info("判断userService是否为空：" + (userService == null));
    }

    public void initMethod() {
        logger.info("【initMethod普通方法】调用InitDemoService.initMethod()");
        System.out.println("Init method: myServiceImpl");
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("【InitializingBean】InitializingBean.afterPropertiesSet()");
    }

    /*@Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        logger.info("【BeanPostProcessor】调用BeanPostProcessor.postProcessBeforeInitialization()");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        logger.info("【BeanPostProcessor】调用BeanPostProcessor.postProcessAfterInitialization()");
        return bean;
    }*/

    @Override
    public void destroy() throws Exception {
        logger.info("【DiposibleBean接口】调用DiposibleBean.destory()");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        logger.info("【ApplicationContextAware接口】调用ApplicationContextAware.setApplicationContext()");
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
        logger.info("【BeanNameAware接口】调用BeanNameAware.setBeanName()");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        logger.info("【BeanFactoryAware接口】调用BeanFactoryAware.setBeanFactory()");
    }
}
