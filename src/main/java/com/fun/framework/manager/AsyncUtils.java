package com.fun.framework.manager;

import com.fun.framework.annotation.enums.LoginType;
import com.fun.common.utils.IpUtils;
import com.fun.common.utils.TimestampUtil;
import com.fun.project.admin.monitor.entity.LoginLog;

import javax.servlet.http.HttpServletRequest;

/**
 * 提取存储公用部分，传递参数到异步工厂
 *
 * @author DJun
 */
public class AsyncUtils {

    public static void excRecordLoginLog(HttpServletRequest request,
                                         String loginName,
                                         String status,
                                         LoginType loginType,
                                         String msg) {
        LoginLog loginLog = new LoginLog();
        String ipAddr = IpUtils.getIpAddr(request);
        loginLog.setCreateTime(TimestampUtil.getCurrentTimestamp13());
        loginLog.setStatus(status);
        loginLog.setMsg(msg);
        loginLog.setIpaddr(ipAddr);
        loginLog.setLoginName(loginName);
        AsyncManager.me().execute(AsyncFactory.recordLogin(loginLog, loginType));
    }

}
