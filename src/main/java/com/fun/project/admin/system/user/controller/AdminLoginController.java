package com.fun.project.admin.system.user.controller;

import com.fun.common.constant.Constants;
import com.fun.common.result.CommonResult;
import com.fun.common.utils.StringUtils;
import com.fun.framework.annotaion.Limit;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.NotBlank;

/**
 * 登录接口
 * @author DJun
 * @date 2019/9/14 9:44
 */
@Controller
@RequestMapping("/admin")
public class AdminLoginController {
    String prefix = "/fun/views";


    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView(prefix + "/login");
    }


    @PostMapping("/login")
    @Limit(key = "login", period = 60, count = 5, name = "后台登录接口", prefix = "limit")
    @ResponseBody
    public CommonResult login(@NotBlank(message = "{required}") String loginName,
                              @NotBlank(message = "{required}") String password,
                              @RequestParam(value = "rememberMe", defaultValue = "false") Boolean rememberMe) {
        UsernamePasswordToken token = new UsernamePasswordToken(loginName, password, rememberMe);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage())) {
                msg = e.getMessage();
            }
            return CommonResult.failed(msg);
        }
        return CommonResult.success(1,Constants.LOGIN_SUCCESS);
    }

    @GetMapping("/unauthorized")
    public String unauthorized() {
        return prefix + "/error/unauthorized";
    }

}
