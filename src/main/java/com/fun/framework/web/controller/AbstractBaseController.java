package com.fun.framework.web.controller;

import com.fun.common.pagehelper.CommonPage;
import com.fun.common.utils.SqlUtil;
import com.fun.common.utils.StringUtils;
import com.github.pagehelper.PageHelper;

/**
 * APP 与 admin 通用部分
 *
 * @author DJun
 * @date 2019/11/27
 */
public abstract class AbstractBaseController {
    /**
     * 从 Servlet 中读取 pageNum 和 pageSize等分页信息
     */
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
