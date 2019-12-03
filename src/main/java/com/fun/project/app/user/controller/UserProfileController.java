package com.fun.project.app.user.controller;

import com.fun.common.constant.Constants;
import com.fun.framework.annotation.NeedLoginToken;
import com.fun.framework.annotation.enums.LoginType;
import com.fun.common.result.CommonResult;
import com.fun.common.utils.StringUtils;
import com.fun.framework.annotation.JwtPermission;
import com.fun.framework.annotation.Log;
import com.fun.framework.web.controller.AppBaseController;
import com.fun.project.app.user.dto.UserDto;
import com.fun.project.app.user.entity.AppUser;
import com.fun.project.app.user.service.IAppUserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


import java.util.ArrayList;
import java.util.List;

import static com.fun.common.result.CommonResult.success;


/**
 * 用户个人信息相关接口
 * @author DJun
 * @date 2019/9/7 16:11
 */
@Api(tags = "App用户信息")
@Controller
@RequestMapping("/app/user")
public class UserProfileController extends AppBaseController {

    @Autowired
    private IAppUserService appUserService;

    @GetMapping("/{userId}")
    @ResponseBody
    public CommonResult selectUserById(@PathVariable("userId") Long userId) {
        return success(appUserService.selectUserById(userId));
    }

    @JwtPermission("user:edit")
    @ApiOperation("用户修改个人信息")
    @Log(value = "用户修改个人信息", type = LoginType.App)
    @PutMapping("/edit")
    @ResponseBody
    public CommonResult editSave(@Valid UserDto user) {
        AppUser appUser = new AppUser();
        appUser.setUserId(user.getUserId());
        appUser.setLoginName(user.getLoginName());

        if (StringUtils.isNotEmpty(user.getUAccount())) {
            appUser.setUAccount(user.getUAccount());
            appUser.setIsLock(Constants.NOT_UNIQUE);
        }
        if (StringUtils.isNotEmpty(user.getPassword())) {
            appUser.setPassword(user.getPassword());
            appUser.setSalt(user.getSalt());
        }
        if (StringUtils.isNotEmpty(user.getSex())) {
            appUser.setSex(user.getSex());
        }
        if (StringUtils.isNotEmpty(user.getAvatar())) {
            appUser.setAvatar(user.getAvatar());
        }
        if (StringUtils.isNotEmpty(user.getUsername())) {
            appUser.setUsername(user.getUsername());
        }
        return success(appUserService.updateAppUser(appUser));
    }

    @NeedLoginToken
    @ApiOperation("RSA算法加密测试")
    @GetMapping("/test")
    @ResponseBody
    public CommonResult testRsa() {
        List<AppUser> userList = new ArrayList<>();
        userList.add(new AppUser("admin1", "aaa1", "pwd"));
        userList.add(new AppUser("admin2", "aaa2", "pwd"));
        userList.add(new AppUser("admin3", "aaa3", "pwd"));
        return success(encrypt(userList));
    }


}
