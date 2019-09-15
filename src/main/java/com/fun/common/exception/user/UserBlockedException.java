package com.fun.common.exception.user;

/**
 * created by DJun on 2019/9/14 21:40
 * desc: 用户锁定异常类
 */
public class UserBlockedException extends UserException {
    private static final long serialVersionUID = 1L;

    public UserBlockedException() {
        super("user.blocked", null);
    }
}
