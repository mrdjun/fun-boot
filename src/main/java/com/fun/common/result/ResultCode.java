package com.fun.common.result;

/**
 * @author DJun
 * @date 2019/07/19 11:56
 */
public enum ResultCode implements IErrorCode{
    /**  code and message */
    SUCCESS(200,"操作成功"),
    WARN(301,"操作警告"),
    FAILED(500,"操作失败"),
    VALIDATE_FAILED(300, "参数检验失败"),
    UNAUTHORIZED(401, "未登录或token已经过期"),
    FORBIDDEN(403, "无相关权限");

    /** HTTP Code */
    private int code;

    /** 自定义提示信息 */
    private String message;

    ResultCode(int code,String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
