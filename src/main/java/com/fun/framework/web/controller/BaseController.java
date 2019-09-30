package com.fun.framework.web.controller;


import com.fun.common.utils.StringUtils;
import com.fun.framework.shiro.helper.ShiroUtils;
import com.fun.project.admin.system.entity.user.AdminUser;


/**
 * @author DJun
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
