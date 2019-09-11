package com.fun.common.utils;

import com.auth0.jwt.JWT;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * created by DJun on 2019/9/8 14:54
 * desc:
 */
public class TokenUtil {

    /**
     * 获取Token中的userId
     */
    public static String getTokenUserId() {
        return JWT.decode(Objects.requireNonNull(getRequest()).getHeader("token")).getAudience().get(0);
    }

    /**
     * 获取Token中的loginName
     */
    public static String getTokenLoginName() {
        return JWT.decode(Objects.requireNonNull(getRequest()).getHeader("token")).getSubject();
    }

    /**
     * 获取request
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        return requestAttributes == null ? null : requestAttributes.getRequest();
    }
}
