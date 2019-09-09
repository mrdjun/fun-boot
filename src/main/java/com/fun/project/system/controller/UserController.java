package com.fun.project.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.fun.common.result.CommonResult;
import com.fun.common.utils.StringUtils;
import com.fun.framework.annotaion.Limit;
import com.fun.framework.annotaion.LimitType;
import com.fun.framework.annotaion.NeedLoginToken;
import com.fun.framework.interceptor.TokenService;
import com.fun.framework.redis.IRedisService;
import com.fun.project.system.entity.User;
import com.fun.project.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * created by DJun on 2019/9/7 16:11
 * desc:
 */
@Api(description = "用户相关api")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    @ApiOperation("用户列表")
    @NeedLoginToken
    @GetMapping("/list")
    @ResponseBody
    public CommonResult getUserList(User user) {
        return CommonResult.success(userService.selectUserList(user));
    }

    @ApiOperation("通过userId查询用户信息")
    @GetMapping("/selectUserById/{userId}")
    @ResponseBody
    public CommonResult selectUserById(@PathVariable("userId") Long userId) {
        return CommonResult.success(userService.selectUserById(userId));
    }

    @ApiOperation("登录")
    @ResponseBody
    @Limit(count = 5, limitType = LimitType.IP, period = 2)
    @PostMapping("/login")
    public CommonResult login(@RequestParam("loginName") String loginName,
                              @RequestParam("password") String password,
                              HttpServletResponse response) {

        User userInfo = userService.login(loginName, password);
        if (StringUtils.isNull(userInfo))
            return CommonResult.failed("账号或密码不正确");

        String token = tokenService.getToken(userInfo);

        Cookie cookie = new Cookie("token", token);
        response.addCookie(cookie);


        return CommonResult.success(loginName);
    }


}
