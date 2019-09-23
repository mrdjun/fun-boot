package com.fun.project.app.user.controller;

import com.fun.common.constant.Constants;
import com.fun.common.constant.LoginType;
import com.fun.common.exception.RedisConnectException;
import com.fun.common.result.CommonResult;
import com.fun.common.utils.*;
import com.fun.framework.annotaion.Limit;
import com.fun.framework.annotaion.Log;
import com.fun.framework.annotaion.NeedLoginToken;
import com.fun.framework.annotaion.enums.LimitType;
import com.fun.framework.interceptor.TokenService;
import com.fun.framework.redis.IRedisService;
import com.fun.project.app.user.entity.AppUser;
import com.fun.project.app.user.service.IAppUserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.validation.constraints.NotBlank;

import static com.fun.framework.manager.AsyncUtils.excRecordLoginLog;

/**
 * 前台用户登录相关业务：
 * 登录、退出登录、注册、找回密码、修改密码
 * @author DJun
 */
@Api(value = "登录", description = "登录管理api", position = 40, produces = "http")
@RestController
@Slf4j
@RequestMapping("/app/user")
public class AppLoginController {
    @Autowired
    private IAppUserService appUserService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private IRedisService iRedisService;

    @ApiOperation(value = "前台登录", notes = "前台登录,200成功，500失败", produces = "application/json, application/xml", consumes = "application/json, application/xml")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginName", value = "登录账号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "操作成功"),
            @ApiResponse(code = 500, message = "操作失败，返回错误原因"),
    })
    @Limit(limitType = LimitType.IP, period = 20, count = 5, name = "登录", prefix = "limit")
    @PostMapping("/login")
    public CommonResult login(@NotBlank String loginName,
                              @NotBlank String password) {

        AppUser appUserInfo = appUserService.login(loginName, password);

        if (StringUtils.isNull(appUserInfo)) {
            excRecordLoginLog(ServletUtils.getRequest(), loginName, Constants.FAIL, LoginType.App, Constants.LOGIN_FAIL);
            return CommonResult.failed(Constants.LOGIN_FAIL);
        }

        String token;
        try {
            token = tokenService.getToken(appUserInfo);
        } catch (RedisConnectException e) {
            log.info("[Redis连接失败，请管理员检查]-[{}]", DateUtils.getTime());
            excRecordLoginLog(ServletUtils.getRequest(), loginName, Constants.FAIL, LoginType.App, Constants.LOGIN_FAIL);
            return CommonResult.failed(Constants.LOGIN_FAIL);
        }
        Cookie cookie = new Cookie("token", token);
        ServletUtils.getResponse().addCookie(cookie);

        excRecordLoginLog(ServletUtils.getRequest(), loginName, Constants.SUCCESS, LoginType.App, Constants.LOGIN_SUCCESS);
        return CommonResult.success(appUserInfo, Constants.LOGIN_SUCCESS);
    }

    @ApiOperation(value = "退出登录", notes = "操作成功自动跳转login页面", produces = "application/json, application/xml", consumes = "application/json, application/xml")
    @NeedLoginToken
    @Log(value = "退出登录",type = LoginType.App)
    @GetMapping("/logout")
    public CommonResult logout() {
        // 此处 Token 无需是否为空，拦截器已验证
        String loginName = TokenUtil.getTokenLoginName();
        try {
            iRedisService.del(loginName);
        } catch (RedisConnectException e) {
            log.info("[Redis连接失败，请管理员检查]-[{}]", DateUtils.getNowDate());
            return CommonResult.failed();
        }
        excRecordLoginLog(ServletUtils.getRequest(), loginName, Constants.SUCCESS, LoginType.App, Constants.LOGOUT);
        return CommonResult.success(Constants.LOGOUT);
    }

}
