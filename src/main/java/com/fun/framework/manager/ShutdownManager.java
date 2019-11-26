package com.fun.framework.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * 确保应用退出时能关闭后台线程
 * @author DJun
 */
@Component
@Slf4j
public class ShutdownManager {

    @PreDestroy
    public void destroy() {
        shutdownAsyncManager();
    }

    /**
     * 停止异步执行任务
     * 关闭后台任务任务线程池
     */
    private void shutdownAsyncManager() {
        try {
            log.info("正在关闭后台任务");
            AsyncManager.me().shutdown();
        } catch (Exception e) {
            log.info("关闭任务时出现了问题");
            log.error(e.getMessage(), e);
        } finally {
            log.info("后台任务清理成功");
        }
    }
}
