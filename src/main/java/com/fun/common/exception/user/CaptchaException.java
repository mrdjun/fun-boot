package com.fun.common.exception.user;

/**
 * created by DJun on 2019/9/14 21:24
 * desc: 验证码错误异常类
 */
public class CaptchaException extends UserException {
    private static final long serialVersionUID = 1L;

    public CaptchaException() {
        super("user.jcaptcha.error", null);
    }
}
