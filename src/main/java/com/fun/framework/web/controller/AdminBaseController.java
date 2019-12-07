package com.fun.framework.web.controller;


import com.fun.common.constant.Constants;
import com.fun.common.pagehelper.CommonPage;
import com.fun.common.utils.SqlUtil;
import com.fun.common.utils.StringUtils;
import com.fun.framework.shiro.helper.ShiroUtils;
import com.fun.project.admin.system.entity.user.AdminUser;
import com.github.pagehelper.PageHelper;


/**
 * Admin 端常用方法封装
 *
 * @author DJun
 */
public class AdminBaseController extends AbstractBaseController{

    /** 页面加前缀跳转 */
    public static String view(String view) {
        return Constants.VIEW_PREFIX + view;
    }

    /** 页面重定向 */
    protected String redirect(String url) {
        return StringUtils.format("redirect:{}", url);
    }

    /** 获取当前用户全部信息 */
    protected AdminUser getSysUser() {
        return ShiroUtils.getSysUser();
    }

    /** 设置当前用户 */
    protected void setSysUser(AdminUser user) {
        ShiroUtils.setSysUser(user);
    }

    /** 获取当前用户的id */
    public Long getUserId() {
        return getSysUser().getUserId();
    }

    /** 获取当前用户登录的账号 */
    public String getLoginName() {
        return getSysUser().getLoginName();
    }
}
