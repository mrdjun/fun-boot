package com.fun.framework.manager;

import com.fun.common.utils.AddressUtils;
import com.fun.common.utils.SpringUtils;
import com.fun.project.monitor.entity.OperLog;
import com.fun.project.monitor.service.IOperLogService;
import lombok.extern.slf4j.Slf4j;

import java.util.TimerTask;

/**
 * created by DJun on 2019/9/9 16:42
 * desc: 异步工厂 （产生任务用）
 */
@Slf4j
public class AsyncFactory {

    // 异步操作任务调度线程池

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

}
