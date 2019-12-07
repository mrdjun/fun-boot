package com.fun.common.result;

/**
 *
 * @author DJun
 * @date 2019/07/19 11:58
 */
public interface IErrorCode {
    /**
     * 获取 code
     *
     * @return code
     */
    int getCode();

    /**
     * 获取 message
     *
     * @return message
     */
    String getMessage();
}
