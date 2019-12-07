package com.fun.common.utils.app;

import com.fun.common.constant.Constants;
import com.fun.common.exception.jwt.JWTDecodeException;
import com.fun.common.exception.jwt.JWTVerificationException;
import com.fun.common.utils.DateUtils;
import com.fun.common.utils.MessageUtils;
import com.fun.common.utils.StringUtils;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * APP端 JWT 工具类
 *
 * @author DJun
 * @date 2019/11/14
 */
public class TokenUtils {
    private static final String ROLE_CLAIMS = "role_key";
    private static final String ISSUER = "MrDJun";
    private static Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * 生成 Token
     *
     * @param userId    Audience
     * @param loginName Subject
     * @param roleKey   Claims
     * @return jws
     */
    public static String createToken(String userId,
                                     String loginName,
                                     String roleKey) {
        Map<String, Object> map = new HashMap<>(1);
        map.put(ROLE_CLAIMS, roleKey);
        return Jwts.builder()
                .signWith(secretKey)
                .setClaims(map)
                .setIssuer(ISSUER)
                .setSubject(loginName)
                .setAudience(userId)
                .setIssuedAt(DateUtils.getNowDate())
                .compact();
    }

    /**
     * 从请求中获取登录账号
     */
    public static String getTokenLoginName() {
        try {
            return tokenBody(Objects.requireNonNull(getRequest()).getHeader(Constants.TOKEN)).getSubject();
        } catch (JWTDecodeException e) {
            throw new JWTVerificationException(MessageUtils.message("jwt.not.valid"));
        }
    }

    /**
     * 从token中获取登录账号
     */
    public static String getTokenLoginName(String token) {
        try {
            return Objects.requireNonNull(tokenBody(token).getSubject());
        } catch (JWTDecodeException e) {
            throw new JWTVerificationException(MessageUtils.message("jwt.not.valid"));
        }
    }

    /**
     * 从请求中获取用户ID
     */
    public static Long getTokenUserId() {
        try {
            return Long.getLong(getTokenUserId(getToken()));
        } catch (JWTDecodeException e) {
            throw new JWTVerificationException(MessageUtils.message("jwt.not.valid"));
        }
    }

    /**
     * 获取用户ID
     */
    public static String getTokenUserId(String token) {
        try {
            return Objects.requireNonNull(tokenBody(token).getAudience());
        } catch (JWTDecodeException e) {
            throw new JWTVerificationException(MessageUtils.message("jwt.not.valid"));
        }
    }

    /**
     * 获取用户角色
     */
    public static String getTokenUserRole(String token) {
        try {
            return (String) tokenBody(token).get(ROLE_CLAIMS);
        } catch (JWTDecodeException e) {
            throw new JWTVerificationException(MessageUtils.message("jwt.not.valid"));
        }
    }

    public static String getTokenUserRole() {
        try {
            return (String) tokenBody(Objects.requireNonNull(getRequest()).getHeader(Constants.TOKEN)).get(ROLE_CLAIMS);
        } catch (JWTDecodeException e) {
            throw new JWTVerificationException(MessageUtils.message("jwt.not.valid"));
        }
    }

    /**
     * 读取 JWS Body
     *
     * @param token jws
     * @return Claims
     */
    private static Claims tokenBody(String token) throws JWTDecodeException {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw new JWTVerificationException(MessageUtils.message("jwt.not.valid"));
        }
    }

    /**
     * 获取request
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        return requestAttributes == null ? null : requestAttributes.getRequest();
    }

    public static String getToken() {
        return getRequest().getHeader(Constants.TOKEN);
    }

}
