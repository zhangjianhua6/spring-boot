package com.jason.web.controller;

import com.jason.web.annotation.AuthIgnore;
import com.jason.web.entity.Test;
import com.jason.web.pojo.response.AopResponse;
import com.jason.web.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("/updateTest")
    @AuthIgnore
    public AopResponse updateTest(Test test){
        testService.update(test);
        return new AopResponse();
    }
}
