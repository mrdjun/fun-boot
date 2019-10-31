package com.fun.framework.web.controller;


import com.fun.common.constant.Constants;
import com.fun.common.pagehelper.CommonPage;
import com.fun.common.utils.SqlUtil;
import com.fun.common.utils.StringUtils;
import com.fun.framework.shiro.helper.ShiroUtils;
import com.fun.project.admin.system.entity.user.AdminUser;
import com.github.pagehelper.PageHelper;


/**
 * 此 Controller 可被直接继承并使用
 *
 * @author DJun
 */
public class BaseController {

    /**
     * 页面加前缀跳转
     */
    public static String view(String view) {
        return Constants.VIEW_PREFIX + view;
    }

    /**
     * 页面重定向
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

    /** 从 Servlet 中读取 pageNum 和 pageSize等分页信息 */
    protected void startPage() {
        CommonPage pageDomain = CommonPage.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }
}
