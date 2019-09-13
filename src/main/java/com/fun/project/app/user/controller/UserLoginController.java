package com.fun.project.app.user.controller;

import com.fun.common.constant.Constants;
import com.fun.common.exception.RedisConnectException;
import com.fun.common.result.CommonResult;
import com.fun.common.utils.*;
import com.fun.framework.annotaion.Limit;
import com.fun.framework.annotaion.Log;
import com.fun.framework.annotaion.NeedLoginToken;
import com.fun.framework.annotaion.enums.LimitType;
import com.fun.framework.interceptor.TokenService;
import com.fun.framework.manager.AsyncFactory;
import com.fun.framework.manager.AsyncManager;
import com.fun.framework.redis.IRedisService;
import com.fun.project.admin.monitor.log.entity.LoginLog;
import com.fun.project.app.user.entity.User;
import com.fun.project.app.user.service.UserService;
import io.swagger.annotations.*;
import jdk.nashorn.internal.parser.Token;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

/**
 * created by DJun on 2019/9/13 10:57
 * desc: 前台用户登录相关业务：
 *       登录、退出登录、注册、找回密码、修改密码
 */
@Api(value = "登录", description = "登录管理api", position = 40, produces = "http")
@RestController
@Slf4j
@RequestMapping("/api/user")
public class UserLoginController {
    @Autowired
    private UserService userService;
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
                              @NotBlank String password,
                              HttpServletResponse response) {

        User userInfo = userService.login(loginName, password);

        if (StringUtils.isNull(userInfo)){
            excRecordLoginLog(loginName, Constants.FAIL,Constants.LOGIN_FAIL);
            return CommonResult.failed(Constants.LOGIN_FAIL);
        }

        String token;
        try {
            token = tokenService.getToken(userInfo);
        } catch (RedisConnectException e) {
            log.info("[Redis连接失败，请管理员检查]-[{}]", DateUtils.getTime());
            excRecordLoginLog(loginName, Constants.FAIL,Constants.LOGIN_FAIL);
            return CommonResult.failed(Constants.LOGIN_FAIL);
        }
        Cookie cookie = new Cookie("token", token);
        response.addCookie(cookie);

        excRecordLoginLog(loginName, Constants.SUCCESS,Constants.LOGIN_SUCCESS);
        return CommonResult.success(userInfo, Constants.LOGIN_SUCCESS);
    }

    @ApiOperation(value = "退出登录", notes = "操作成功自动跳转login页面",produces="application/json, application/xml", consumes="application/json, application/xml")
    @NeedLoginToken
    @GetMapping("/logout")
    public CommonResult logout(){
        // 此处 Token 无需是否为空，拦截器已验证
        String loginName = TokenUtil.getTokenLoginName();
        try {
            iRedisService.del(loginName);
        } catch (RedisConnectException e) {
            log.info("[Redis连接失败，请管理员检查]-[{}]", DateUtils.getNowDate());
            return CommonResult.failed();
        }
        excRecordLoginLog(loginName, Constants.SUCCESS,Constants.LOGOUT);
        return CommonResult.success(Constants.LOGOUT);
    }

    private void excRecordLoginLog(String loginName,String status,String msg){
        HttpServletRequest request = ServletUtils.getRequest();
        String ipAddr = IpUtils.getIpAddr(request);
        LoginLog loginLog = new LoginLog();
        loginLog.setCreateTime(System.currentTimeMillis());
        loginLog.setStatus(status);
        loginLog.setMsg(msg);
        loginLog.setIpaddr(ipAddr);
        loginLog.setLoginName(loginName);
        AsyncManager.me().execute(AsyncFactory.recordLogin(loginLog));
    }
}
