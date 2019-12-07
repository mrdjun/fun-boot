package com.fun.common.exception.jwt;

/**
 * JWT解码异常
 *
 * @author DJun
 * @date 2019/11/16
 */
public class JWTDecodeException extends JWTVerificationException {
    public JWTDecodeException(String message) {
        this(message, null);
    }

    public JWTDecodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
