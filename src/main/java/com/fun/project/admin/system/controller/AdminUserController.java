package com.fun.project.admin.system.controller;

import com.fun.common.constant.Constants;
import com.fun.common.pagehelper.CommonPage;
import com.fun.common.result.CommonResult;
import com.fun.common.utils.ServletUtils;
import com.fun.framework.annotaion.Limit;
import com.fun.project.admin.system.entity.user.AdminUser;
import com.fun.project.admin.system.service.IAdminUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
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
@Api("")
public class AdminUserController {

    @Autowired
    private IAdminUserService adminUserService;


    @ApiOperation(value = "异步登录",notes = "如果是Ajax请求，返回Json字符串")
    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        if (ServletUtils.isAjaxRequest(request)) {
            return ServletUtils.renderString(response, "{\"code\":\"1\",\"msg\":\"未登录或登录超时。请重新登录\"}");
        }
        return Constants.view( "/login");
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
    public ModelAndView userIndex() {
        return new ModelAndView(Constants.view( "system/user/user"));
    }

    @GetMapping("/user/add")
    @ResponseBody
    public CommonResult addUser(@Validated AdminUser user) {
        if (adminUserService.checkLoginNameUnique(user.getLoginName()) < 0) {
            return CommonResult.failed("失败，账号'" + user.getLoginName() + "' 已存在");
        } else if (adminUserService.checkEmailUnique(user.getEmail()) < 0) {
            return CommonResult.failed("失败，邮箱'" + user.getEmail() + "' 已存在");
        } else if (adminUserService.checkPhoneUnique(user.getTelephone()) < 0) {
            return CommonResult.failed("失败，手机号码'" + user.getTelephone() + "' 已存在");
        }

        return CommonResult.success(adminUserService.insertUser(user));
    }

    @PostMapping("/user/list")
    @ResponseBody
    public CommonResult<CommonPage<AdminUser>> getAdminUserList(AdminUser adminUser,
                                                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        List<AdminUser> list = adminUserService.selectAdminUserList(adminUser, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(list));
    }

    @ApiOperation(value = "异步验证用户名是否唯一",notes = "返回0，则不存在")
    @PostMapping("/user/checkLoginNameUnique")
    @ResponseBody
    public CommonResult checkLoginNameUniqueAjax(@Validated AdminUser user) {
        if (adminUserService.checkLoginNameUnique(user.getLoginName()) < 0) {
            return CommonResult.failed("失败，账号'" + user.getLoginName() + "' 已存在");
        }
        return CommonResult.success(Constants.SUCCESS);
    }

    @ApiOperation(value = "异步验证邮箱是否唯一",notes = "返回0，则不存在")
    @PostMapping("/user/checkEmailUnique")
    @ResponseBody
    public CommonResult checkEmailUniqueAjax(@Validated AdminUser user) {
        if (adminUserService.checkEmailUnique(user.getEmail()) < 0) {
            return CommonResult.failed("失败，邮箱'" + user.getEmail() + "' 已存在");
        }
        return CommonResult.success(Constants.SUCCESS);
    }

    @ApiOperation(value = "异步验证手机号码是否唯一",notes = "返回0，则不存在")
    @PostMapping("/user/checkPhoneUnique")
    @ResponseBody
    public CommonResult checkPhoneUniqueAjax(@Validated AdminUser user) {
        if (adminUserService.checkPhoneUnique(user.getTelephone()) < 0) {
            return CommonResult.failed("失败，手机号码'" + user.getTelephone() + "' 已存在");
        }
        return CommonResult.success(Constants.SUCCESS);
    }

}
