package com.fun.common.exception.user;

/**
 * created by DJun on 2019/9/14 21:28
 * desc: 用户不存在异常类
 */
public class UserNotExistsException extends UserException {
    private static final long serialVersionUID = 1L;

    public UserNotExistsException() {
        super("user.not.exists", null);
    }
}
