package com.fun.project.app.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.fun.common.constant.Constants;
import com.fun.framework.annotation.PassToken;
import com.fun.framework.annotation.enums.LoginType;
import com.fun.common.result.CommonResult;
import com.fun.common.utils.StringUtils;
import com.fun.framework.annotation.JwtPermission;
import com.fun.framework.annotation.Log;
import com.fun.framework.web.service.EncryptService;
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
 * App端 -> 用户个人信息相关接口
 *
 * @author DJun
 * @date 2019/9/7 16:11
 */
@Api(tags = "App用户信息")
@RestController
@RequestMapping("/app/user")
public class UserProfileController {

    @Autowired
    private IAppUserService appUserService;
    @Autowired
    private EncryptService encryptService;

    @PassToken
    @ApiOperation(value = "获取RSA公钥和私钥", notes = "公钥用于加密app发送服务器的数据，私钥用于解开服务器发送到App的数据")
    @PostMapping("/rsa/keys")
    public CommonResult getRsaKeys(String loginName) {
        encryptService.initRsa(loginName);
        return success(encryptService.genRsaKeys());
    }

    @PassToken
    @ApiOperation("App端RSA加密，服务器解密")
    @PostMapping("/rsa/decrypt")
    public CommonResult testDecryptAppData(String loginName,String publicKey) {
        String decodeData = encryptService.rsaDecrypt(publicKey,loginName);
        JSONObject jsonObject = null;
        if (StringUtils.isNotEmpty(decodeData)){
             jsonObject = JSONObject.parseObject(decodeData);
        }
        return success(jsonObject);
    }

    @PassToken
    @ApiOperation("服务器加密，App端解密")
    @GetMapping("/rsa/list")
    @ResponseBody
    public CommonResult testRsaEncrypt(String loginName) {
        List<AppUser> userList = new ArrayList<>();
        userList.add(new AppUser("admin1", "aaa1", "pwd"));
        userList.add(new AppUser("admin2", "aaa2", "pwd"));
        userList.add(new AppUser("admin3", "aaa3", "pwd"));
        return success(encryptService.rsaEncrypt(userList,loginName));
    }


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

}
