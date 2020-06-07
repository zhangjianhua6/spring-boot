package com.jason.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * @author: zhangjianhua
 * @date: 2019-12-30 15:16
 **/
@Service
@Order(Integer.MAX_VALUE)
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    public UserService() {
        logger.info("【构造函数】调用构造函数，实例化UserService对象");
    }
}
