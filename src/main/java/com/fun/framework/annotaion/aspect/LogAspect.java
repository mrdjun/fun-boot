package com.fun.framework.annotaion.aspect;

import com.alibaba.fastjson.JSONObject;
import com.fun.common.utils.*;
import com.fun.framework.annotaion.enums.BusinessStatus;
import com.fun.framework.config.FunBootConfig;
import com.fun.framework.manager.AsyncFactory;
import com.fun.framework.manager.AsyncManager;
import com.fun.project.admin.monitor.log.entity.OperLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fun.framework.annotaion.Log;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

import java.util.Map;

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

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()")
    public void doAfterReturning(JoinPoint joinPoint) {
        handleLog(joinPoint, null);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e);
    }

    private void handleLog(final JoinPoint joinPoint, final Exception e) {
        // 如果没开启日志记录则直接退出
        if (!funBootConfig.isOpenLog())
            return;
        // 获取当前登录用户账号
        String currUserLoginName = TokenUtil.getTokenLoginName();

        // 获取当前时间的时间戳
        long beginTime = System.currentTimeMillis();
        try {
            // 获得注解
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null)
                return;
            // 获取用户ip
            HttpServletRequest request = ServletUtils.getRequest();
            String ip = IpUtils.getIpAddr(request);
            // 获取注解上的操作描述
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            Log logAnnotation = method.getAnnotation(Log.class);
            String operName = logAnnotation.value();
            // 获取当前操作的方法名
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            // 封装对象
            OperLog operLog = new OperLog();
            operLog.setStatus(String.valueOf(BusinessStatus.SUCCESS.ordinal()));
            operLog.setOperIp(ip);
            operLog.setOperName(operName);
            operLog.setLoginName(currUserLoginName);
            operLog.setCreateTime(beginTime);
            if (e != null) {
                operLog.setStatus(String.valueOf(BusinessStatus.FAIL.ordinal()));
                operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            operLog.setMethod(className + "." + methodName + "()");
            setRequestValue(operLog);
            AsyncManager.me().execute(AsyncFactory.recordOper(operLog,beginTime));

        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }

    }

    /**
     * 是否存在 @Log，存在就获取
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
     * @param log     日志
     * @param operLog 操作日志
     * @throws Exception e
     */
    public void getControllerMethodDescription(Log log, OperLog operLog)
            throws Exception {

        // 用于扩展 @Log 的参数

    }

    /**
     * 获取请求的参数，放到log中
     */
    private void setRequestValue(OperLog operLog) {
        Map<String, String[]> map = ServletUtils.getRequest().getParameterMap();
        String params = JSONObject.toJSONString(map);
        operLog.setOperParam(StringUtils.substring(params, 0, 2000));
    }
}
