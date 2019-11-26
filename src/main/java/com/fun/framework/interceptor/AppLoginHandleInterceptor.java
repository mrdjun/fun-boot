package com.fun.framework.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.fun.common.exception.jwt.JWTVerificationException;
import com.fun.common.result.CommonResult;
import com.fun.common.utils.ServletUtils;
import com.fun.common.utils.StringUtils;
import com.fun.common.utils.app.TokenUtils;
import com.fun.framework.annotation.PassToken;
import com.fun.framework.annotation.NeedLoginToken;
import com.fun.framework.web.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/***
 * JWT Token 拦截验证
 * 默认拦截 /app/** 的所有请求
 * @author DJun
 */
public class AppLoginHandleInterceptor implements HandlerInterceptor {
    @Autowired
    private TokenService tokenService;

    @Value("${jwt.userPrefix}")
    private String userPrefix;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse,
                             Object object) {
        String token = TokenUtils.getToken();

        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();

        // 检查 @NeedLoginToken 注解，若存在可避免再次获取PassToken
        if (method.isAnnotationPresent(NeedLoginToken.class)) {
            NeedLoginToken needLoginToken = method.getAnnotation(NeedLoginToken.class);
            if (needLoginToken.required()) {
                return isLogin(token, httpServletResponse);
            }
        }

        // 检查 @PassToken ，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            return passToken.required();
        } else {
            return isLogin(token, httpServletResponse);
        }

    }

    /**
     * 处理完成后，如果需要重新刷新token
     * 在此处，注入TokenService调用refresh方法，并把新的token放在Cookie中返回给App
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView)
            throws Exception {

    }

    /**
     * 这个方法是无论上面两个方法执行是否成功，这里都会执行
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e)
            throws Exception {

    }

    /**
     * 判断当前用户是否已经登录
     *
     * @param token    token
     * @param response HttpServletResponse
     * @return 已登录-true
     */
    private boolean isLogin(final String token, HttpServletResponse response) {
        // token 无效退出
        try {
            if (StringUtils.isNull(token) || !tokenService.isValidToken(token)) {
                return respUnAuth(response);
            }
        } catch (JWTVerificationException e) {
            return respUnAuth(response);
        }
        return true;
    }

    /**
     * 未登录或token失效401状态返回
     *
     * @param response HttpServletResponse
     * @return false
     */
    private boolean respUnAuth(HttpServletResponse response) {
        CommonResult commonResult = CommonResult.unauthorized(false);
        ServletUtils.renderString(response, JSONObject.toJSONString(commonResult));
        return false;
    }
}
