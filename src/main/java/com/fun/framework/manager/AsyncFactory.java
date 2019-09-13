package com.fun.framework.manager;

import com.fun.common.utils.*;
import com.fun.project.admin.monitor.log.entity.LoginLog;
import com.fun.project.admin.monitor.log.entity.OperLog;
import com.fun.project.admin.monitor.log.service.IOperLogService;
import com.fun.project.admin.monitor.log.service.LoginLogServiceImpl;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.TimerTask;

/**
 * created by DJun on 2019/9/9 16:42
 * desc: 异步工厂 （产生任务用）
 * 异步操作任务调度线程池
 */
@Slf4j
public class AsyncFactory {
    /**
     * 日志输出为 sys-user 类型
     */
    private static final Logger sys_user_logger = LoggerFactory.getLogger("sys-user");

    /**
     * 操作日志记录
     *
     * @param operLog 操作日志信息
     * @return 任务task
     */
    public static TimerTask recordOper(final OperLog operLog, long beginTime) {
        return new TimerTask() {
            @Override
            public void run() {
                // 获取远程查询操作地点
                operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
                long time = System.currentTimeMillis() - beginTime;
                operLog.setTime(time);
                SpringUtils.getBean(IOperLogService.class).insertOperlog(operLog);
            }
        };
    }

    /**
     * 登录日志记录
     *
     * @param args 列表
     * @return 任务Task
     */
    public static TimerTask recordLogin(final LoginLog loginLog,
                                        final Object... args) {
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));

        return new TimerTask() {
            @Override
            public void run() {
                // 获取IP
                String ipAddr = loginLog.getIpaddr();
                // 获取地点
                String location = AddressUtils.getRealAddressByIP(ipAddr);
                // 获取客户端操作系统
                String os = userAgent.getOperatingSystem().getName();
                // 获取客户端浏览器
                String browser = userAgent.getBrowser().getName();

                // 输出到日志，提高性能，故不用String
                StringBuilder s = new StringBuilder();
                s.append(LogUtils.getBlock(ipAddr));
                s.append(location);
                s.append(LogUtils.getBlock(loginLog.getLoginName()));
                s.append(LogUtils.getBlock(loginLog.getStatus()));
                s.append(LogUtils.getBlock(loginLog.getMsg()));
                sys_user_logger.info(s.toString(), args);

                // 输出到数据库
                loginLog.setLoginLocation(location);
                loginLog.setOs(os);
                loginLog.setBrowser(browser);
                SpringUtils.getBean(LoginLogServiceImpl.class).insertLoginLog(loginLog);
            }
        };
    }


}
