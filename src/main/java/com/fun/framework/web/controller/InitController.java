package com.fun.framework.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by DJun on 2019/9/9 17:23
 * desc:
 */
@RestController
public class InitController {
    @GetMapping("")
    public String login(){
        return "登录页面正在熬夜开发中...";
    }

    @GetMapping("index")
    public String index(){
        return "index";
    }

}
