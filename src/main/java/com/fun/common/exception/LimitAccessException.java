package com.fun.common.exception;

/**
 * 接口限流异常类
 *
 * @author DJun
 */
public class LimitAccessException extends Exception {

    private static final long serialVersionUID = -3608667856397125671L;

    public LimitAccessException(String message) {
        super(message);
    }
}