package com.fun.common.exception.user;

/**
 * 用户密码不正确或不符合规范异常类
 *
 * @author DJun
 * @date 2019/9/14 12:17
 */
public class UserPasswordNotMatchException extends UserException {
    private static final long serialVersionUID = 1L;

    public UserPasswordNotMatchException() {
        super("user.password.not.match", null);
    }
}
