package com.fun.framework.web.controller;


import com.fun.common.utils.StringUtils;
import com.fun.framework.shiro.ShiroUtils;
import com.fun.project.admin.system.user.entity.AdminUser;

/**
 * created by DJun on 2019/9/9 17:23
 * desc:
 */
public class BaseController {

    /**
     * 页面跳转
     */
    public String redirect(String url) {
        return StringUtils.format("redirect:{}", url);
    }

    public AdminUser getSysUser() {
        return ShiroUtils.getSysUser();
    }

    public void setSysUser(AdminUser user) {
        ShiroUtils.setSysUser(user);
    }

    public Long getUserId() {
        return getSysUser().getUserId();
    }

    public String getLoginName() {
        return getSysUser().getLoginName();
    }
}
