package com.jason.web.controller;

import com.jason.web.annotation.AuthIgnore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    private volatile int userNum = 0;

    @RequestMapping("/login")
    @ResponseBody
    @AuthIgnore
    public String login(){
        return "Hello, SpringBoot With Docker";
    }

    @RequestMapping("/")
    @ResponseBody
    @AuthIgnore
    public String hello() {
        return "Hello, SpringBoot With Docker";
    }


    @RequestMapping("/index")
    @AuthIgnore
    public String index(Model model){
        model.addAttribute("msg", "hello, this is index!");
        return "index";
    }

}
