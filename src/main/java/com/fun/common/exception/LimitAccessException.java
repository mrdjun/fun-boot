package com.fun.common.exception;

/**
 * created by DJun on 2019/9/7 17:53
 * desc: 接口限流异常类
 */
public class LimitAccessException extends Exception {

    private static final long serialVersionUID = -3608667856397125671L;

    public LimitAccessException(String message) {
        super(message);
    }
}