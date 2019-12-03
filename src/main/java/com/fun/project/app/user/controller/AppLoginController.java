package com.fun.project.app.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.fun.common.constant.Constants;
import com.fun.framework.annotation.enums.LoginType;
import com.fun.common.result.CommonResult;
import com.fun.common.utils.*;
import com.fun.common.utils.app.AppRandomUtils;
import com.fun.common.utils.app.TokenUtils;
import com.fun.framework.annotation.Limit;
import com.fun.framework.annotation.Log;
import com.fun.framework.annotation.PassToken;
import com.fun.framework.annotation.enums.LimitType;
import com.fun.framework.web.controller.AppBaseController;
import com.fun.framework.web.service.TokenService;
import com.fun.project.app.user.dto.UserDto;
import com.fun.project.app.user.entity.AppUser;
import com.fun.project.app.user.service.IAppUserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static com.fun.common.result.CommonResult.*;
import static com.fun.framework.manager.AsyncUtils.excRecordLoginLog;

/**
 * 前台用户登录相关业务：
 * 登录、退出登录、注册、找回密码、修改密码
 *
 * @author DJun
 */
@Api(tags = "app用户登录注册", position = 40, produces = "http")
@RestController
@RequestMapping("/app")
public class AppLoginController{
    @Autowired
    private IAppUserService appUserService;
    @Autowired
    private TokenService tokenService;

    @ApiOperation(value = "前台登录", notes = "前台登录,200成功，500失败", produces = "application/json, application/xml", consumes = "application/json, application/xml")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginName", value = "登录账号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "rememberMe", value = "记住登录，默认为false", dataType = "boolean", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "操作成功"),
            @ApiResponse(code = 500, message = "操作失败，返回错误原因"),
    })
    @Limit(limitType = LimitType.IP, period = 20, count = 5, name = "登录", prefix = "login")
    @PostMapping("/login")
    @PassToken
    public CommonResult login(@NotBlank String loginName,
                              @NotBlank String password,
                              @RequestParam(defaultValue = "false", required = false) Boolean rememberMe) {

        JSONObject data = appUserService.login(loginName, password);

        if (StringUtils.isNotNull(data.get(Constants.EMPTY))) {
            excRecordLoginLog(ServletUtils.getRequest(), loginName, Constants.FAIL, LoginType.App, Constants.LOGIN_FAIL);
            return failed(Constants.LOGIN_FAIL);
        }

        String token = tokenService.getToken(data, rememberMe);

        if (StringUtils.isEmpty(token)) {
            return failed(Constants.LOGIN_FAIL);
        }

        data.put("token", token);
        excRecordLoginLog(ServletUtils.getRequest(), loginName, Constants.SUCCESS, LoginType.App, Constants.LOGIN_SUCCESS);
        return success(data, Constants.LOGIN_SUCCESS);
    }

    @ApiOperation(value = "用户注册", notes = "部分属性用户选填")
    @ApiResponses({
            @ApiResponse(code = 200, message = "操作成功"),
            @ApiResponse(code = 301, message = "警告，登录账号、邮箱或手机号码重复"),
            @ApiResponse(code = 400, message = "校验错误")
    })
    @PassToken
    @Log(value = "用户注册",type = LoginType.App)
    @Limit(limitType = LimitType.IP, period = 10, count = 5, name = "注册", prefix = "register")
    @PostMapping("/register")
    public CommonResult register(@Valid UserDto user) {
        AppUser appUser = new AppUser();
        // 校验登录账号
        if (Constants.NOT_UNIQUE.equals(appUserService.checkLoginNameUnique(user))) {
            return warn("注册失败，登录账号：'" + user.getLoginName() + "'，已存在");
        }
        // 校验邮箱
        if (StringUtils.isNotEmpty(user.getEmail())) {
            if (Constants.NOT_UNIQUE.equals(appUserService.checkEmailUnique(user))) {
                return warn("注册失败，邮箱：'" + user.getEmail() + "'，已存在");
            }
            appUser.setEmail(user.getEmail().toLowerCase());
        }
        // 校验电话号码
        if (StringUtils.isNotEmpty(user.getTelephone())) {
            if (Constants.NOT_UNIQUE.equals(appUserService.checkEmailUnique(user))) {
                return warn("注册失败，手机号：'" + user.getTelephone() + "'，已存在");
            }
            appUser.setTelephone(user.getTelephone());
        }
        if (StringUtils.isEmpty(user.getUsername())) {
            appUser.setUsername(AppRandomUtils.getUsername());
        }
        if (StringUtils.isEmpty(user.getAvatar())) {
            appUser.setAvatar(AppRandomUtils.getHead());
        }
        appUser.setLoginName(user.getLoginName());
        appUser.setPassword(user.getPassword());
        appUser.setSalt(user.getSalt());
        appUser.setUAccount(AppRandomUtils.getStr16());

        return success(appUserService.insertUser(appUser));
    }

    @ApiOperation(value = "退出登录", notes = "操作成功跳转到登录页面", produces = "application/json, application/xml", consumes = "application/json, application/xml")
    @Log(value = "退出登录", type = LoginType.App)
    @GetMapping("/logout")
    public CommonResult logout() {
        String loginName = TokenUtils.getTokenLoginName();
        if (StringUtils.isEmpty(loginName)) {
            CommonResult.unauthorized("未登录或token已失效");
        }
        tokenService.delToken(loginName);
        excRecordLoginLog(ServletUtils.getRequest(), loginName, Constants.SUCCESS, LoginType.App, Constants.LOGOUT);
        return success(Constants.LOGOUT);
    }

    @ApiOperation(value = "刷新token", notes = "刷新成功后，之前的token会立即失效")
    @Log(value = "刷新token", type = LoginType.App)
    @PostMapping("/refreshToken")
    public CommonResult refreshToken(HttpServletRequest request,
                                     @RequestParam(defaultValue = "false", required = false) Boolean rememberMe) {
        String newToken = tokenService.getRefreshToken(request.getHeader(Constants.TOKEN), rememberMe);
        if (StringUtils.isEmpty(newToken)) {
            return failed("token已失效或不正确");
        }
        return success(newToken);
    }



}
