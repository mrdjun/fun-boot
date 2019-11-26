package com.fun.project.app.user.controller;

import com.fun.common.constant.Constants;
import com.fun.common.constant.LoginType;
import com.fun.common.result.CommonResult;
import com.fun.common.utils.StringUtils;
import com.fun.framework.annotation.JwtPermission;
import com.fun.framework.annotation.Log;
import com.fun.framework.annotation.NeedLoginToken;
import com.fun.framework.web.controller.AppBaseController;
import com.fun.project.app.user.dto.UserDto;
import com.fun.project.app.user.entity.AppUser;
import com.fun.project.app.user.service.IAppUserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static com.fun.common.result.CommonResult.success;


/**
 * @author DJun
 * @date 2019/9/7 16:11
 */
@Api(tags = "用户信息")
@RestController
@RequestMapping("/app/user")
public class AppUserController extends AppBaseController {

    @Autowired
    private IAppUserService appUserService;

    @NeedLoginToken
    @GetMapping("/{userId}")
    public CommonResult selectUserById(@PathVariable("userId") Long userId) {
        return success(appUserService.selectUserById(userId));
    }

    @JwtPermission("user:edit")
    @ApiOperation("修改用户数据")
    @Log(value = "修改用户数据", type = LoginType.App)
    @PutMapping("/edit")
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
    @GetMapping("/test")
    public CommonResult testRsa() {
        List<AppUser> userList = new ArrayList<>();
        userList.add(new AppUser("admin1", "aaa1", "pwd"));
        userList.add(new AppUser("admin2", "aaa2", "pwd"));
        userList.add(new AppUser("admin3", "aaa3", "pwd"));
        return success(encrypt(userList));
    }

}
