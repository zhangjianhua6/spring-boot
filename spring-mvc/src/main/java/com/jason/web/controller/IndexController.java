package com.jason.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.jason.web.pojo.response.AopResponse;
import com.jason.web.pojo.response.InfoReturn;
import com.jason.web.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @RequestMapping("/")
    @AuthIgnore
    public String login(){

        return "Hello, SpringBoot With Docker";
    }

    @RequestMapping("/")
    @AuthIgnore
    public String hello() {
        return "Hello, SpringBoot With Docker";
    }

}
