package com.fun.common.exception.jwt;

/**
 * JWT验证异常
 *
 * @author DJun
 * @date 2019/11/16
 */
public class JWTVerificationException extends RuntimeException {
    public JWTVerificationException(String message) {
        this(message, null);
    }

    public JWTVerificationException(String message, Throwable cause) {
        super(message, cause);
    }
}
