package com.fun.framework.annotation.aspect;


import com.alibaba.fastjson.JSONObject;
import com.fun.common.constant.Constants;
import com.fun.common.exception.RedisConnectException;
import com.fun.common.result.CommonResult;
import com.fun.common.utils.DateUtils;
import com.fun.common.utils.ServletUtils;
import com.fun.common.utils.StringUtils;
import com.fun.common.utils.app.TokenUtils;
import com.fun.common.utils.text.Convert;
import com.fun.framework.annotation.JwtPermission;
import com.fun.framework.redis.IRedisService;
import com.fun.framework.web.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


/**
 * APP端验证用户权限
 *
 * @author DJun
 * @date 2019/11/25 11:20
 */
@Aspect
@Slf4j
@Component
public class JwtPermissionAspect {
    @Autowired
    private IRedisService redisService;
    @Autowired
    private TokenService tokenService;

    public JwtPermissionAspect() {
    }

    @Pointcut("@annotation(com.fun.framework.annotation.JwtPermission)")
    public void JwtPointcut() {
    }

    @Around("JwtPointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        HttpServletResponse response = ServletUtils.getResponse();
        // 替代 @NeedLoginToken 验证是否存在
        String token = TokenUtils.getToken();
        if (StringUtils.isNull(token) || !tokenService.isValidToken(token)) {
            respForbidden(response);
        }

        String roleKey = TokenUtils.getTokenUserRole();
        String loginName = TokenUtils.getTokenLoginName();

        // 如果是(admin)管理员直接放行
        if (roleKey.equals(Constants.ADMIN)) {
            return point.proceed();
        }

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        JwtPermission permAnnotation = method.getAnnotation(JwtPermission.class);
        String currPerm = permAnnotation.value();

        if (StringUtils.isEmpty(currPerm)){
            return point.proceed();
        }

        String userInfoStr = null;
        try {
            userInfoStr = redisService.get(loginName);
        } catch (RedisConnectException e) {
            log.error("Redis断开连接-[{}]", DateUtils.getNowDate());
            CommonResult commonResult = CommonResult.failed("系统异常");
            ServletUtils.renderString(response, JSONObject.toJSONString(commonResult));
        }
        JSONObject userInfo = JSONObject.parseObject(userInfoStr);
        String str = Convert.str(userInfo.get("perms"), Constants.UTF8);
        String[] perms = Convert.arrayStrToArr(str);

        boolean flag = false;
        for (String p : perms) {
            if (currPerm.equals(p)) {
                flag = true;
                break;
            }
        }
        if (flag) {
            return point.proceed();
        } else {
            respForbidden(response);
        }
        return null;
    }

    private void respForbidden(HttpServletResponse response) {
        CommonResult commonResult = CommonResult.forbidden(false);
        ServletUtils.renderString(response, JSONObject.toJSONString(commonResult));
    }
}
