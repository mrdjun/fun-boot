package com.fun.framework.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Verification;
import com.fun.framework.exception.RedisConnectException;
import com.fun.framework.redis.IRedisService;
import com.fun.project.system.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * created by DJun on 2019/9/8 14:53
 * desc: 颁发令牌 ： 下发token
 */
@Service("TokenService")
public class TokenService {
    @Autowired
    private IRedisService iRedisService;

    /**  加密算法 可以抽象到环境变量中配置 */
    private String MAC_NAME = "HMacSHA256";

    /**
     * 秘钥生成器
     */
    private KeyGenerator keyGenerator;

    /**
     * JWT 校验
     */
    public static JWTVerifier jwtVerifier;

    /**
     * 校验器 用于生成 JWTVerifier 校验器
     */
    public static Verification verification;

    @PostConstruct
    @Scheduled(cron = "0 5 * * *  ?")
    public void initAndRefresh() throws NoSuchAlgorithmException {

        if (null == keyGenerator) {
            keyGenerator = KeyGenerator.getInstance(MAC_NAME);
        }

        SecretKey secretKey = keyGenerator.generateKey();

        Algorithm algorithm = Algorithm.HMAC256(secretKey.getEncoded());
        verification = JWT.require(algorithm);
        jwtVerifier = verification.build();
    }


    /**
     * 生成 JWT-Token
     *
     * 每一次生成token之前，判断缓存里面是否已经有token
     * 生成token之后，存入缓存
     */
    public String getToken(User user) {
        String token;

        Date start = new Date();
        long currentTime = System.currentTimeMillis() + 60 * 60 * 1000;//一小时有效时间
        Date end = new Date(currentTime);

        token = JWT.create().withAudience(user.getUserId().toString())
                .withIssuedAt(start)
                .withExpiresAt(end)
                .sign(Algorithm.HMAC256(user.getPassword()));

        try {
            iRedisService.set(user.getLoginName(), token, 3600000L);
        } catch (RedisConnectException e) {
            e.printStackTrace();
        }

        return token;
    }
}