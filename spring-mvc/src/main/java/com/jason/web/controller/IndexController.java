package com.jason.web.controller;

import com.jason.web.annotation.AuthIgnore;
import com.jason.web.annotation.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @Autowired
    private IndexController indexController;

    @Log(isIgnore = true)
    public void loginInternal(String logKey){
        System.err.println(logKey);
    }

    @RequestMapping("/")
    @AuthIgnore
    public String hello(Model model) {
        indexController.loginInternal("logKey");
        model.addAttribute("msg", "hello, this is index!");
        return "index";
    }


    @RequestMapping("/index")
    @AuthIgnore
    public String index(Model model){
        indexController.loginInternal("logKey");
        model.addAttribute("msg", "hello, this is index!");
        return "index";
    }

}
