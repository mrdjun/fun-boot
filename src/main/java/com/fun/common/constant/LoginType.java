package com.fun.common.constant;

import lombok.Getter;

/**
 * created by DJun on 2019/9/14 12:27
 * desc: 登录类型：前台、后台
 */
@Getter
public enum LoginType {

    App("0","app"),admin("1","admin");

    private final String code;
    private final String info;

    LoginType(String code,String info){
        this.code = code;
        this.info = info;
    }

}
