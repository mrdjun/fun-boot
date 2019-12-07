package com.fun.framework.handler;

import org.springframework.http.HttpStatus;

import java.util.HashMap;


/**
 * 处理全局异常拦截时的消息回复
 *
 * @author DJun
 */
public class FunBootResponse extends HashMap<String, Object> {

    private static final long serialVersionUID = -8713837118340960775L;

    public FunBootResponse code(HttpStatus status) {
        this.put("code", status.value());
        return this;
    }

    public FunBootResponse message(String message) {
        this.put("message", message);
        return this;
    }

    public FunBootResponse data(Object data) {
        this.put("data", data);
        return this;
    }

    public FunBootResponse success() {
        this.code(HttpStatus.OK);
        return this;
    }

    public FunBootResponse fail() {
        this.code(HttpStatus.INTERNAL_SERVER_ERROR);
        return this;
    }

    @Override
    public FunBootResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
