package com.fun.framework.web.service;

import com.alibaba.fastjson.JSONObject;

import com.fun.common.exception.RedisConnectException;
import com.fun.common.utils.DateUtils;
import com.fun.framework.config.AppConfig;
import com.fun.framework.redis.IRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import static com.fun.common.utils.encrypt.RsaUtils.*;
import static com.fun.common.utils.encrypt.RsaUtils.verifyRsaSignature;

/**
 * 常用加密方法
 *
 * @author DJun
 * @date 2019/12/4
 */
@Service
public class EncryptService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /** 返回给App的密匙 */
    private Map<String, String> resKeys = new HashMap<>(2);

    /** 登录超时时间 */
    private final static long LOGIN_EXPIRE = 120000;

    @Autowired
    private AppConfig appConfig;
    @Autowired
    private IRedisService redisService;

    public EncryptService() {}

    public void initRsa(String flagKey) {
        Map<String, String> keys1 = createKeys();
        Map<String, String> keys2 = createKeys();
        try {
            redisService.set(PUBLIC_KEY + flagKey, keys1.get(PUBLIC_KEY), LOGIN_EXPIRE);
            redisService.set(PRIVATE_KEY + flagKey, keys2.get(PRIVATE_KEY), LOGIN_EXPIRE);
        } catch (RedisConnectException e) {
            logger.error("Redis断开连接,{}", DateUtils.getNowDate());
            return;
        }
        this.resKeys.put("publicKey", keys1.get(PUBLIC_KEY));
        this.resKeys.put("privateKey", rsaPrivateKeySign(keys2.get(PRIVATE_KEY)));
    }

    /**
     * 生成两对公私钥
     * 将第一对的公钥和第二对的私钥签名后返回给App端
     * 与之对应，则将第一对的私钥和第二对的公钥保存在服务器
     * 注：此处使用 RSA 仅作为示例，因只作为登录使用，故存入缓存，登录成功后清除
     *
     * @return 第一对的公钥和第二对加签的私钥
     */
    public Map genRsaKeys() {
        return this.resKeys;
    }

    /**
     * 公钥加密
     *
     * flag 相当于用户的唯一标识符，若RSA算法不用做登录，用作数据加密
     * 则可以使用 TokenService 从token中读取出当前登录用户的 loginName 作为 flag。
     * 若用作登录，则将登录的账号作为 flag 。通过 flag 取出为该用户生产的密匙。
     */
    public String rsaEncrypt(Object data, String flag) {
        try {
            return publicEncrypt(JSONObject.toJSONString(data), getPublicKey(redisService.get(PUBLIC_KEY + flag)));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | RedisConnectException e) {
            logger.error("RSA加密异常,{}", e.getMessage());
            return null;
        }
    }

    /** 私钥解密 */
    public String rsaDecrypt(String encodeData, String flag) {
        try {
            return privateDecrypt(encodeData, getPrivateKey(redisService.get(PRIVATE_KEY + flag)));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | RedisConnectException e) {
            logger.error("RSA解密异常,{}", e.getMessage());
            return null;
        }

    }

    /** 私钥签名 */
    public String rsaPrivateKeySign(String privateKey) {
        try {
            return rsaSignature(privateKey, appConfig.getSignature());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException
                | InvalidKeyException | SignatureException e) {
            logger.error("RSA签名异常,{}", e.getMessage());
            return null;
        }
    }

    /** 公钥验签 */
    public boolean verifyRsaPrivateKeySign(String signStr, String flag) {
        try {
            return verifyRsaSignature(redisService.get(PUBLIC_KEY + flag), signStr, appConfig.getSignature());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException
                | SignatureException | RedisConnectException e) {
            logger.error("RSA验签异常,{}", e.getMessage());
            return false;
        }
    }
}
