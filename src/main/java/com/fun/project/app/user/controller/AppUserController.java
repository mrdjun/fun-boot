package com.fun.project.app.user.controller;

import com.fun.common.constant.LoginType;
import com.fun.common.result.CommonResult;
import com.fun.framework.annotation.Log;
import com.fun.framework.annotation.NeedLoginToken;
import com.fun.project.app.user.entity.AppUser;
import com.fun.project.app.user.service.IAppUserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * created by DJun on 2019/9/7 16:11
 * desc:
 */
@Api(description = "用户相关业务api")
@RestController
@RequestMapping("/app/user")
public class AppUserController {

    @Autowired
    private IAppUserService appUserService;

    @ApiOperation("用户列表")
    @Log("获取用户列表")
    @GetMapping("/list")
    public CommonResult getUserList(AppUser appUser,
                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        List<AppUser> list = appUserService.selectUserList(appUser,pageNum,pageSize);
        return CommonResult.success(list);
    }

    @NeedLoginToken
    @ApiOperation("通过userId查询用户信息")
    @Log(value = "通过userId查询用户信息",type = LoginType.App)
    @GetMapping("/selectUserById/{userId}")
    public CommonResult selectUserById(@PathVariable("userId") Long userId) {
        return CommonResult.success(appUserService.selectUserById(userId));
    }


}
