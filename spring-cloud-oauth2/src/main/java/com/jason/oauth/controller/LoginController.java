package com.jason.oauth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @RequestMapping(value = "/login‐success", produces = {"text/plain;charset=UTF-8"})
    public String loginSuccess(){
        return "登录成功";
    }
}
