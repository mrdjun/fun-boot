package com.fun.project.admin.monitor.log.service;

import com.fun.project.admin.monitor.log.entity.LoginLog;

import java.util.List;

/**
 * created by DJun on 2019/9/13 12:41
 * desc:
 */
public interface ILoginLogService {
    /**
     * 新增系统登录日志
     *
     * @param loginLog 访问日志对象
     */
    void insertLoginLog(LoginLog loginLog);

    /**
     * 查询系统登录日志集合
     *
     * @param loginLog 访问日志对象
     * @return 登录记录集合
     */
    List<LoginLog> selectLoginLogList(LoginLog loginLog,int pageNum,int pageSize);

    /**
     * 批量删除系统登录日志
     *
     * @param ids 需要删除的数据
     * @return 结果
     */
    int deleteLoginLogByIds(String ids);

    /**
     * 清空系统登录日志
     *
     * @return 结果
     */
    int cleanLoginLog();

}
