package com.jason.web.controller;

import com.jason.web.annotation.AuthIgnore;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @RequestMapping("/index")
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
