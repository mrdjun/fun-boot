package com.fun.framework.annotation.aspect;

import com.alibaba.fastjson.JSONObject;
import com.fun.framework.annotation.enums.LoginType;
import com.fun.common.utils.*;
import com.fun.common.utils.app.TokenUtils;
import com.fun.framework.annotation.enums.BusinessStatus;
import com.fun.framework.config.FunBootConfig;
import com.fun.framework.manager.AsyncFactory;
import com.fun.framework.manager.AsyncManager;
import com.fun.framework.shiro.helper.ShiroUtils;
import com.fun.project.admin.monitor.entity.OperLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fun.framework.annotation.Log;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

import java.util.Map;

/**
 * 操作日志记录处理
 *
 * @author DJun
 * @date 2019/9/9 19:49
 */
@Aspect
@Component
@Slf4j
public class LogAspect {
    @Autowired
    private FunBootConfig funBootConfig;


    /**
     * 配置织入点
     */
    @Pointcut("@annotation(com.fun.framework.annotation.Log)")
    public void logPointCut() {}

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
        if (!funBootConfig.isOpenLog()) {
            return;
        }
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            Log logAnnotation = method.getAnnotation(Log.class);
            // 获取操作名称
            String operName = logAnnotation.value();
            // 获取LoginType
            String currLoginType = logAnnotation.type().getCode();
            String currUserLoginName;
            // 获取当前app端用户登录账号
            if (currLoginType.equals(LoginType.App.getCode())) {
                currUserLoginName = TokenUtils.getTokenLoginName();
            }
            // 若当前用户为后台用户
            else {
                currUserLoginName = ShiroUtils.getLoginName();
            }

            long beginTime = System.currentTimeMillis();

            // 获得注解
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }

            // 获取用户ip
            HttpServletRequest request = ServletUtils.getRequest();
            String ip = IpUtils.getIpAddr(request);

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
            // 处理设置注解上的参数
            getControllerMethodDescription(controllerLog, operLog);
            AsyncManager.me().execute(AsyncFactory.recordOper(operLog, beginTime));

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
     * 获取并保存@Log的其它参数的值
     *
     * @param log     @Log 注解对象
     * @param operLog 操作日志
     * @throws Exception e
     */
    private void getControllerMethodDescription(Log log, OperLog operLog)
            throws Exception {
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            setRequestValue(operLog);
        }
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
