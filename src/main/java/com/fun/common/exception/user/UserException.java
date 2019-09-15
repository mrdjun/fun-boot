package com.fun.common.exception.user;

import com.fun.common.exception.base.BaseException;

/**
 * created by DJun on 2019/9/14 12:03
 * desc: 用户信息异常类
 */
public class UserException extends BaseException {
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args) {
        super("user", code, args, null);
    }

}
