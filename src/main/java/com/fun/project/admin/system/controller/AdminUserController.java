package com.fun.project.admin.system.controller;

import com.fun.common.constant.Constants;
import com.fun.common.pageHelper.CommonPage;
import com.fun.common.result.CommonResult;
import com.fun.common.utils.ServletUtils;
import com.fun.common.utils.StringUtils;
import com.fun.framework.annotaion.Limit;
import com.fun.project.admin.system.entity.user.AdminUser;
import com.fun.project.admin.system.service.IAdminUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author DJun
 */
@Controller
@RequestMapping("/admin")
public class AdminUserController {

    @Autowired
    private IAdminUserService adminUserService;

    /**
     * 异步登录处理
     * 如果是Ajax请求，返回Json字符串。
     */
    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        if (ServletUtils.isAjaxRequest(request)) {
            return ServletUtils.renderString(response, "{\"code\":\"1\",\"msg\":\"未登录或登录超时。请重新登录\"}");
        }
        return Constants.VIEW_PREFIX + "/login";
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
            return CommonResult.failed("用户名或密码错误");
        }
        return CommonResult.success(Constants.LOGIN_SUCCESS);
    }


    @GetMapping("/user")
    public ModelAndView user() {
        return new ModelAndView(Constants.VIEW_PREFIX + "system/user/user");
    }

    @PostMapping("/list")
    @ResponseBody
    public CommonResult<CommonPage<AdminUser>> getAdminUserList(AdminUser adminUser,
                                                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        List<AdminUser> list = adminUserService.selectUserList(adminUser, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(list));
    }


}
