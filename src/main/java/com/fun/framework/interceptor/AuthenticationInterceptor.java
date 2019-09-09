package com.fun.framework.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fun.common.result.CommonResult;
import com.fun.common.utils.ServletUtils;
import com.fun.common.utils.StringUtils;
import com.fun.framework.annotaion.PassToken;
import com.fun.framework.annotaion.NeedLoginToken;
import com.fun.framework.redis.IRedisService;
import com.fun.project.system.entity.User;
import com.fun.project.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;
    @Autowired
    private IRedisService iRedisService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object)
            throws Exception {
        // 从 http 请求头中取出 token
        String token = httpServletRequest.getHeader("token");
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();

        //检查是否有 @PassToken ，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }

        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(NeedLoginToken.class)) {
            NeedLoginToken userLoginToken = method.getAnnotation(NeedLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    CommonResult commonResult = CommonResult.failed("无token，请重新登录");
                    commonResult.setCode(401);
                    ServletUtils.renderString(httpServletResponse, JSONObject.toJSONString(commonResult));
                    return false;
                }

                // 获取 token 中的 userId
                String userId;
                try {
                    userId = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j) {
                    CommonResult commonResult = CommonResult.failed("Token已失效");
                    commonResult.setCode(401);
                    ServletUtils.renderString(httpServletResponse, JSONObject.toJSONString(commonResult));
                    return false;
                }

                User user = userService.selectUserById(Long.parseLong(userId));

                if (StringUtils.isNull(user)) {
                    CommonResult commonResult = CommonResult.failed("用户不存在");
                    ServletUtils.renderString(httpServletResponse, JSONObject.toJSONString(commonResult));
                    return false;
                }

                // 第二次生成token后，使上一次的token 过期
                String currToken = iRedisService.get(user.getLoginName());
                if (!currToken.equals(token)) {
                    CommonResult commonResult = CommonResult.failed("Token已失效");
                    commonResult.setCode(401);
                    ServletUtils.renderString(httpServletResponse, JSONObject.toJSONString(commonResult));
                    return false;
                }

                // 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();

                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    CommonResult commonResult = CommonResult.failed("Token已失效");
                    commonResult.setCode(401);
                    ServletUtils.renderString(httpServletResponse, JSONObject.toJSONString(commonResult));
                    return false;
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

}
