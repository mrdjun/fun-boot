package com.fun.common.exception.file;

/**
 * created by DJun on 2019/9/12 18:51
 * desc: 文件名称超长限制异常类
 */
public class FileNameLengthLimitExceededException extends FileException {
    private static final long serialVersionUID = 1L;

    public FileNameLengthLimitExceededException(int defaultFileNameLength) {
        super("upload.filename.exceed.length", new Object[]{defaultFileNameLength});
    }
}
