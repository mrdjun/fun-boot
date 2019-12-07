package com.fun.common.exception.user;

/**
 * 用户不存在异常类
 *
 * @author DJun
 * @date 2019/9/14 21:28
 */
public class UserNotExistsException extends UserException {
    private static final long serialVersionUID = 1L;

    public UserNotExistsException() {
        super("user.not.exists", null);
    }
}
