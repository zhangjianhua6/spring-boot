package com.jason.web.controller;

import com.jason.web.annotation.AuthIgnore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebSocketController {

    @RequestMapping("/websocket-javax")
    @AuthIgnore
    public String javax(Model model){
        model.addAttribute("msg","hello , this is websocket javax impl!");
        return "websocket-javax";
    }

    @RequestMapping("/websocket-spring")
    @AuthIgnore
    public String spring(Model model){
        model.addAttribute("msg","hello , this is websocket spring impl!");
        return "websocket-spring";
    }
}
