package com.fun.project.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fun.common.result.CommonResult;
import com.fun.project.system.entity.User;
import com.fun.project.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by DJun on 2019/9/7 16:11
 * desc:
 */
@Api(description = "用户相关api")
@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService userService ;

    @ApiOperation("用户列表")
    @GetMapping("/list")
    @ResponseBody
    public CommonResult getUserList(User user){
        return CommonResult.success(JSONObject.toJSON(userService.selectUserList(user)));
    }

}
