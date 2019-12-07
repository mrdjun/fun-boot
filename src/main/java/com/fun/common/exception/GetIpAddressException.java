package com.fun.common.exception;

/**
 * 获取IP地址异常
 *
 * @author DJun
 * @date 2019/9/30
 */
public class GetIpAddressException extends Exception {

    private static final long serialVersionUID = 1L;

    public GetIpAddressException(String message) {
        super(message);
    }
}
