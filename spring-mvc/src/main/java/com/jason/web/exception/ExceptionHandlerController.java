package com.jason.web.exception;

import com.jason.web.pojo.response.AopResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: zhangjianhua
 * @date: 2019-12-07 16:00
 **/
@ControllerAdvice
public class ExceptionHandlerController {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(AuthException.class)
    @ResponseBody
    public AopResponse  AuthExceptionHandler(AuthException e){
        logger.error("权限认证异常", e);
        return new AopResponse(AopResponse.AUTH_ERROR, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public AopResponse exceptionHandler(Exception e){
        logger.error("系统异常", e);
        return new AopResponse(AopResponse.SYS_ERROR, "系统繁忙");
    }

}
