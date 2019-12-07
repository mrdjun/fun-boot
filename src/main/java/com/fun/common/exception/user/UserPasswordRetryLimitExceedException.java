package com.fun.common.exception.user;


/**
 * 密码输入错误次数过多，锁定10分钟
 *
 * @author DJun
 * @date 2019/9/14 11:39
 */
public class UserPasswordRetryLimitExceedException extends UserException {
    private static final long serialVersionUID = 1L;

    public UserPasswordRetryLimitExceedException(int retryLimitCount) {
        super("user.password.retry.limit.exceed", new Object[]{retryLimitCount});
    }
}
