package com.fun.project.admin.system.controller;

import com.fun.common.constant.Constants;
import com.fun.common.result.CommonResult;
import com.fun.common.utils.ServletUtils;
import com.fun.common.utils.StringUtils;
import com.fun.framework.annotation.Limit;
import com.fun.framework.web.controller.AdminBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

/**
 * 登录注册
 * 目前后台用户不允许注册，账号由超级管理员新增
 *
 * @author DJun
 * @date 2019/10/31
 */
@Api(tags = {"admin用户登录注册"})
@Controller
@RequestMapping("/admin")
public class AdminLoginController {

    @GetMapping("/login")
    public String loginPage(HttpServletRequest request, HttpServletResponse response) {
        if (ServletUtils.isAjaxRequest(request)) {
            return ServletUtils.renderString(response, "{\"code\":\"1\",\"msg\":\"未登录或登录超时。请重新登录\"}");
        }
        return AdminBaseController.view("/login");
    }

    @ApiOperation(value = "异步登录", notes = "如果是Ajax请求，返回Json字符串")
    @PostMapping("/login")
    @Limit(key = "login", period = 60, count = 5, name = "后台登录接口", prefix = "limit")
    @ResponseBody
    public CommonResult loginAsync(@NotBlank(message = "{required}") String loginName,
                                   @NotBlank(message = "{required}") String password,
                                   @RequestParam(value = "rememberMe", defaultValue = "false") Boolean rememberMe) {
        UsernamePasswordToken token = new UsernamePasswordToken(loginName, password, rememberMe);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (AuthenticationException e ) {
            String msg = "账号或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage())){
                msg = e.getMessage();
            }
            return CommonResult.failed(msg);
        }

        return CommonResult.success(Constants.LOGIN_SUCCESS);
    }

}
