package com.fun.framework.web.controller;

import com.alibaba.fastjson.JSONObject;

import com.fun.framework.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

import static com.fun.common.utils.encrypt.RsaUtils.*;
import static com.fun.common.utils.encrypt.RsaUtils.getPrivateKey;

/**
 * App端常用方法封装
 *
 * @author DJun
 * @date 2019/11/16
 */
public class AppBaseController extends AbstractBaseController{
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private AppConfig appConfig;

    /**
     * 公钥加密
     */
    protected String encrypt(Object data) {
        try {
            return publicEncrypt(JSONObject.toJSONString(data), getPublicKey(appConfig.getPublicKey()));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error("RSA加密异常,{}", e.getMessage());
            return null;
        }
    }

    /**
     * 私钥解密
     */
    protected String decrypt(String encodeData) {
        try {
            return privateDecrypt(encodeData, getPrivateKey(appConfig.getPrivateKey()));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error("RSA解密异常,{}", e.getMessage());
            return null;
        }

    }

    /**
     * 私钥签名
     */
    protected String privateKeySign(String privateKey) {
        try {
            return rsaSignature(privateKey, appConfig.getSignature());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | SignatureException e) {
            logger.error("RSA签名异常,{}", e.getMessage());
            return null;
        }
    }

    /**
     * 公钥验签
     */
    protected boolean verifyPrivateKeySign(String signStr) {
        try {
            return verifyRsaSignature(appConfig.getPublicKey(), signStr, appConfig.getSignature());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | SignatureException e) {
            logger.error("RSA验签异常,{}", e.getMessage());
            return false;
        }
    }

}
