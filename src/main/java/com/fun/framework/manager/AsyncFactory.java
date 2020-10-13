package com.fun.framework.manager;

import com.alibaba.fastjson.JSON;
import com.fun.common.exception.RedisConnectException;
import com.fun.framework.annotation.enums.LoginType;
import com.fun.common.utils.*;
import com.fun.framework.redis.RedisServiceImpl;
import com.fun.project.admin.monitor.entity.LoginLog;
import com.fun.project.admin.monitor.entity.OperLog;
import com.fun.project.admin.monitor.service.IOperLogService;
import com.fun.project.admin.monitor.service.impl.LoginLogServiceImpl;
import com.fun.project.admin.system.entity.user.AdminUser;
import com.fun.project.admin.system.service.impl.AdminUserServiceImpl;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

/**
 * 异步工厂 （产生任务用）
 * 异步操作任务调度线程池
 *
 * @author DJun
 * @date 2019/04/10 15:20
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
                                        final LoginType loginType,
                                        final Object... args) {
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("user-agent"));

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

                StringBuilder s = new StringBuilder();
                s.append(LogUtils.getBlock(ipAddr));
                s.append(location);
                s.append(LogUtils.getBlock(loginLog.getLoginName()));
                s.append(LogUtils.getBlock(loginLog.getStatus()));
                s.append(LogUtils.getBlock(loginLog.getMsg()));
                sys_user_logger.info(s.toString(), args);

                loginLog.setLoginLocation(location);
                loginLog.setOs(os);
                loginLog.setBrowser(browser);

                // 更新APP用户最后一次登录信息
                if (loginType == LoginType.App) {

                } else {
                    // 更新Admin用户最后一次登录信息
                    AdminUser adminUser = new AdminUser();
                    adminUser.setLoginDate(TimestampUtil.getCurrentTimestamp13());
                    adminUser.setLoginIp(ipAddr);
                    adminUser.setLoginName(loginLog.getLoginName());
                    SpringUtils.getBean(AdminUserServiceImpl.class).updateUserInfoByLoginName(adminUser);
                }

                SpringUtils.getBean(LoginLogServiceImpl.class).insertLoginLog(loginLog);
            }
        };
    }

    /**
     * 异步通过账号保存用户个人信息到缓存
     *
     * @param loginName   账号
     * @param milliscends 保存时间（ms）
     * @return task
     */
    public static TimerTask getAppUserInfoToCache(final String loginName,final Long milliscends) {
        return new TimerTask() {
            @Override
            public void run() {
//                AppUser userInfo = SpringUtils.getBean(AppUserServiceImpl.class).selectAppUserByLoginName(loginName);
//                try {
//                    SpringUtils.getBean(RedisServiceImpl.class).set(loginName, JSON.toJSONString(userInfo),milliscends);
//                } catch (RedisConnectException e) {
//                    log.error("redis 连接失败-{}",DateUtils.getNowDate());
//                }
            }
        };
    }


}
