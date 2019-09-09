package com.fun.framework.aspect;

import com.fun.common.utils.IPUtil;
import com.fun.common.utils.ServletUtils;
import com.fun.framework.config.FunBootConfig;
import com.fun.project.monitor.entity.OperLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fun.framework.annotaion.Log;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * created by DJun on 2019/9/9 19:49
 * desc: 操作日志记录处理
 */
@Aspect
@Component
@Slf4j
public class LogAspect {
    @Autowired
    private FunBootConfig funBootConfig;

    // 配置织入点
    @Pointcut("@annotation(com.fun.framework.annotaion.Log)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result;
        long beginTime = System.currentTimeMillis();
        // 执行方法
        result = point.proceed();
//        getHttpServletRequest
        HttpServletRequest request = ServletUtils.getRequest();
        // 设置 IP地址
        String ip = IPUtil.getIpAddr(request);

        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        if (funBootConfig.isOpenLog()) {
            // 获取注解上的描述
            MethodSignature signature = (MethodSignature) point.getSignature();
            Method method = signature.getMethod();
            Log logAnnotation = method.getAnnotation(Log.class);
            String value = "";
            if (logAnnotation != null) {
                value = logAnnotation.value();
            }
            // TODO: 保存日志到数据库
            log.info("当前时间[{}]，IP为：[{}]，操作[{}]，执行时长{}ms", beginTime,ip,value,time);
        }
        return result;
    }

    /**
     * 是否存在 @Log，是就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(Log.class);
        }

        return null;
    }
    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log 日志
     * @param operLog 操作日志
     * @throws Exception e
     */
    public void getControllerMethodDescription(Log log, OperLog operLog) throws Exception{

    }

}
