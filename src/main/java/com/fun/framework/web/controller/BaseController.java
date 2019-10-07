package com.fun.framework.web.controller;


import com.fun.common.utils.StringUtils;
import com.fun.framework.shiro.helper.ShiroUtils;
import com.fun.project.admin.system.entity.user.AdminUser;


/**
 * 此 Controller 可被直接继承并使用
 * @author DJun
 */
public class BaseController {

    /**
     * 页面跳转
     */
    public String redirect(String url) {
        return StringUtils.format("redirect:{}", url);
    }

    /**
     * 获取当前用户全部信息
     */
    public AdminUser getSysUser() {
        return ShiroUtils.getSysUser();
    }

    /**
     * 设置当前用户
     */
    public void setSysUser(AdminUser user) {
        ShiroUtils.setSysUser(user);
    }

    /**
     * 获取当前用户的id
     */
    public Long getUserId() {
        return getSysUser().getUserId();
    }

    /**
     * 获取当前用户登录的账号
     */
    public String getLoginName() {
        return getSysUser().getLoginName();
    }
}
