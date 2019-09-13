package com.fun.framework.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * created by DJun on 2019/9/9 17:23
 * desc:
 */
@Controller
public class InitController {
    @GetMapping("")
    public String login(){
        return "index";
    }

    @GetMapping("index")
    public String index(){
        return "index";
    }

}
