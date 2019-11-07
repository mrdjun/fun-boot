package com.fun.framework.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fun.common.exception.RedisConnectException;
import com.fun.common.result.CommonResult;
import com.fun.common.utils.ServletUtils;
import com.fun.common.utils.StringUtils;
import com.fun.framework.annotation.PassToken;
import com.fun.framework.annotation.NeedLoginToken;
import com.fun.framework.redis.IRedisService;
import com.fun.project.app.user.entity.AppUser;
import com.fun.project.app.user.service.IAppUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/***
 * JWT Token 拦截验证
 * 默认拦截 /app/ 的所有请求
 * @author DJun
 */
public class AppLoginHandleInterceptor implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(AppLoginHandleInterceptor.class);
    @Autowired
    private IAppUserService appUserService;
    @Autowired
    private IRedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse,
                             Object object)
            throws Exception {

        // 从 http 请求头中取出 token
        String token = httpServletRequest.getHeader("token");
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();

        // 检查是否存在 NeedLoginToken 注解，若存在可避免再次获取PassToken
        if (method.isAnnotationPresent(NeedLoginToken.class)) {
            NeedLoginToken needLoginToken = method.getAnnotation(NeedLoginToken.class);
            if (needLoginToken.required()) {
                return isLogin(token,httpServletResponse);
            }
        }

        //检查是否有 @PassToken ，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            return passToken.required();
        }else {
            return isLogin(token,httpServletResponse);
        }

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView)
            throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e)
            throws Exception {

    }

    private boolean isLogin(final String token,HttpServletResponse httpServletResponse){
        // 没有token
        if (StringUtils.isNull(token)) {
            return respUnauth(httpServletResponse);
        }
        // 获取 token 中的 userId
        String userId;
        String loginName;
        try {
            userId = JWT.decode(token).getAudience().get(0);
            loginName = JWT.decode(token).getSubject();
        } catch (JWTDecodeException j) {
            return respUnauth(httpServletResponse);
        }

        // 缓存中是否有当前用户登陆的token
        String currToken = null;
        try {
            currToken = redisService.get(loginName);
        } catch (RedisConnectException e) {
            logger.error("请检查redis的连接,{}",e.getMessage());
        }

        // 第二次生成token后，使上一次的token 过期
        if (StringUtils.isNull(currToken) || !currToken.equals(token)) {
            return respUnauth(httpServletResponse);
        }

        AppUser appUser = appUserService.selectUserById(Long.parseLong(userId));

        if (StringUtils.isNull(appUser)) {
            return respUnauth(httpServletResponse);
        }

        // 验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(appUser.getPassword())).build();

        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            return respUnauth(httpServletResponse);
        }
        return true;
    }

    private boolean respUnauth(HttpServletResponse response){
        CommonResult commonResult = CommonResult.unauthorized(false);
        ServletUtils.renderString(response, JSONObject.toJSONString(commonResult));
        return false;
    }
}
