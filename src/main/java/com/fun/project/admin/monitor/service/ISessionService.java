package com.fun.project.admin.monitor.service;


import com.fun.project.admin.monitor.entity.OnlineUser;

import java.util.List;

/**
 * Session 操作
 *
 * @author DJun
 * @date 2019/9/15 22:58
 */
public interface ISessionService {
    /**
     * 获取在线用户列表
     *
     * @param loginName 登录账号
     * @return List<OnlineUser>
     */
    List<OnlineUser> list(String loginName);

    /**
     * 踢出用户
     *
     * @param sessionIds sessionId
     */
    void forceLogout(String sessionIds);
}
