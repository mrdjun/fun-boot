package com.fun.common.exception;

/**
 * created by DJun on 2019/9/7 17:02
 * desc: Redis 连接异常
 */
public class RedisConnectException extends Exception {

    private static final long serialVersionUID = 1639374111871115063L;

    public RedisConnectException(String message) {
        super(message);
    }
}
