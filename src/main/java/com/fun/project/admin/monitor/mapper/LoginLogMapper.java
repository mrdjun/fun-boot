package com.fun.project.admin.monitor.mapper;

import com.fun.project.admin.monitor.entity.LoginLog;

import java.util.List;

/**
 * 登录日志
 *
 * @author DJun
 * @date 2019/9/13 12:36
 */
public interface LoginLogMapper {
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
    List<LoginLog> selectLoginLogList(LoginLog loginLog);

    /**
     * 批量删除系统登录日志
     *
     * @param ids 需要删除的数据
     * @return 结果
     */
    int deleteLoginLogByIds(String[] ids);

    /**
     * 清空系统登录日志
     *
     * @return 结果
     */
    int cleanLoginLog();
}
