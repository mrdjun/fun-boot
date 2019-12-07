package com.fun.common.exception.file;

import com.fun.common.exception.base.BaseException;

/**
 * 文件信息异常类
 * @author DJun
 * @date 2019/9/12 18:47
 */
public class FileException extends BaseException {
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args) {
        super("file", code, args, null);
    }

}
