package com.fun.framework.manager;

import com.fun.common.constant.LoginType;
import com.fun.common.utils.IpUtils;
import com.fun.project.admin.monitor.log.entity.LoginLog;

import javax.servlet.http.HttpServletRequest;

/**
 * created by DJun on 2019/9/14 12:09
 * desc:
 */
public class AsyncUtils {

    public static void excRecordLoginLog(HttpServletRequest request,
                                         String loginName,
                                         String status,
                                         LoginType loginType,
                                         String msg) {
        String ipAddr = IpUtils.getIpAddr(request);
        LoginLog loginLog = new LoginLog();
        loginLog.setCreateTime(System.currentTimeMillis());
        loginLog.setStatus(status);
        loginLog.setMsg(msg);
        loginLog.setIpaddr(ipAddr);
        loginLog.setLoginName(loginName);
        AsyncManager.me().execute(AsyncFactory.recordLogin(loginLog, loginType));
    }

}
