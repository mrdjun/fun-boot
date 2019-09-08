package com.fun.framework.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fun.common.utils.StringUtils;
import com.fun.framework.exception.RedisConnectException;
import com.fun.framework.redis.IRedisService;
import com.fun.project.system.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * created by DJun on 2019/9/8 14:53
 * desc: 颁发令牌 ： 下发token
 */
@Service("TokenService")
@Slf4j
public class TokenService {
    @Autowired
    private IRedisService iRedisService;

    /**
     * 生成 JWT-Token
     *
     * 每一次生成token之前，判断缓存里面是否已经有token
     * 生成token之后，存入缓存
     */
    public String getToken(User user) {
        String token;
        String isGetNull = null;
        try {
            isGetNull = iRedisService.get(user.getLoginName());
        } catch (RedisConnectException e) {
            e.printStackTrace();
        }
        // 不为空则直接返回，下面就不执行了
        if (StringUtils.isNotEmpty(isGetNull))
            return isGetNull;

        Date start = new Date();
        long currentTime = System.currentTimeMillis() + 60 * 60 * 1000;//一小时有效时间
        Date end = new Date(currentTime);

        token = JWT.create().withAudience(user.getUserId().toString())
                .withIssuedAt(start)
                .withExpiresAt(end)
                .sign(Algorithm.HMAC256(user.getPassword()));

        try {
            log.info(iRedisService.set(user.getLoginName(), token));
        } catch (RedisConnectException e) {
            e.printStackTrace();
        }

        return token;
    }
}