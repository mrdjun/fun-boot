package com.fun.common.exception;

/**
 * 系统异常
 *
 * @author DJun
 */
public class FunBootException extends Exception {
    private static final long serialVersionUID = -994962710559017255L;
    public FunBootException(String message) {
        super(message);
    }
}
