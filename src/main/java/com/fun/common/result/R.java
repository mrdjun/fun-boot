package com.fun.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 统一结果返回类
 *
 * @author DJun
 * @date 2019/07/19 11:49
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class R<T> implements Serializable {
    private int code;
    private String message;
    private T data;

    /**
     * 成功
     *
     * @param data 传入数据
     */
    public static <T> R<T> success(T data) {
        return new R<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功
     * 自定义提示信息
     *
     * @param data    传入数据
     * @param message 提示信息
     */
    public static <T> R<T> success(T data, String message) {
        return new R<>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败
     * 自定义错误
     *
     * @param errorCode 错误
     */
    public static <T> R<T> failed(IErrorCode errorCode) {
        return new R<>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 失败
     */
    public static <T> R<T> failed() {
        return failed(ResultCode.FAILED);
    }

    /**
     * 失败
     * 自定义提示信息
     */
    public static <T> R<T> failed(String message) {
        return new R<>(ResultCode.FAILED.getCode(), message, null);
    }

    /**
     * 参数验证失败 500
     */
    public static <T> R<T> validateFailed() {
        return failed(ResultCode.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败 300
     * 自定义提示信息
     */
    public static <T> R<T> validateFailed(String message) {
        return new R<>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * 未登录401
     */
    public static <T> R<T> unauthorized(T data) {
        return new R<>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未授权403
     */
    public static <T> R<T> forbidden(T data) {
        return new R<>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
    }

    /**
     * 警告301
     */
    public static <T> R<T> warn(String message) {
        return new R<>(ResultCode.WARN.getCode(), message, null);
    }
}
