package com.fun.common.exception.file;

/**
 * created by DJun on 2019/9/12 18:46
 * desc: 文件名大小限制异常类
 */
public class FileSizeLimitExceededException extends FileException {
    private static final long serialVersionUID = 1L;

    public FileSizeLimitExceededException(long defaultMaxSize) {
        super("upload.exceed.maxSize", new Object[]{defaultMaxSize});
    }
}
