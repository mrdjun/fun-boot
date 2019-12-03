package com.fun.framework.web.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fun.common.exception.RedisConnectException;
import com.fun.common.utils.DateUtils;
import com.fun.framework.config.AppConfig;
import com.fun.framework.redis.IRedisService;
import com.fun.project.app.user.entity.AppUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.fun.common.utils.app.TokenUtils.*;

/**
 * @author DJun
 * @date 2019/11/15
 */
@Service("tokenService")
public class TokenService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IRedisService redisService;

    @Autowired
    private AppConfig appConfig;


    /**
     * 生成 Token
     *
     * @param userInfo     AppUser
     * @param isRememberMe 是否记住登录，用 Redis 的 ttl 实现
     * @return token
     */
    public String getToken(JSONObject userInfo, boolean isRememberMe) {
        long expireTime = isRememberMe ? appConfig.getExpirationRemember() : appConfig.getExpiration();
        AppUser appUser = (AppUser) userInfo.get("user");
        String token = createToken(appUser.getUserId().toString(),
                appUser.getLoginName(), appUser.getRoleKey());
        try {
            redisService.set(appConfig.getUserPrefix() + appUser.getLoginName(), token, expireTime);
            redisService.set(appUser.getLoginName(), JSON.toJSONString(userInfo), expireTime);
        } catch (RedisConnectException e) {
            logger.error("redis连接失败-[{}]", DateUtils.dateTime());
            return null;
        }

        return token;
    }

    /**
     * 刷新 Token
     *
     * @param oldToken     刷新前的 Token
     * @param isRememberMe 是否记住
     * @return token
     */
    public String getRefreshToken(String oldToken, boolean isRememberMe) {
        long expireTime = isRememberMe ? appConfig.getExpirationRemember() : appConfig.getExpiration();
        String loginName = getTokenLoginName(oldToken);
        String token = null;
        try {
            if (isValidToken(oldToken)) {
                token = createToken(getTokenUserId(oldToken), getTokenLoginName(oldToken),
                        getTokenUserRole(oldToken));
                if (StringUtils.isEmpty(token)) {
                    return null;
                }
            }
            String userInfo = redisService.get(loginName);
            // 替换Token
            redisService.set(appConfig.getUserPrefix() + loginName, token, expireTime);
            redisService.set(loginName, userInfo, expireTime);
        } catch (RedisConnectException e) {
            logger.error("redis连接失败-[{}]", DateUtils.dateTime());
            return null;
        }
        return token;
    }

    /**
     * 删除 Token
     *
     * @param loginName loginName
     */
    public void delToken(String loginName) {
        try {
            if (StringUtils.isEmpty(redisService.get(appConfig.getUserPrefix() + loginName))) {
                return;
            }
            redisService.del(appConfig.getUserPrefix() + loginName);
        } catch (RedisConnectException e) {
            logger.error("redis连接失败-[{}]", DateUtils.dateTime());
        }
    }

    /**
     * 验证 Token 是否有效
     *
     * @param token jwt
     * @return 有效-true
     */
    public boolean isValidToken(String token) {
        String loginName = getTokenLoginName(token);
        if (StringUtils.isEmpty(loginName)) {
            return false;
        }
        try {
            String oldToken = redisService.get(appConfig.getUserPrefix() + loginName);
            if (StringUtils.isNotEmpty(oldToken) && oldToken.equals(token)) {
                return true;
            }
        } catch (RedisConnectException e) {
            logger.error("redis连接失败-[{}]", DateUtils.dateTime());
            return false;
        }
        return false;
    }

}
