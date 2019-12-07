package com.fun.framework.annotation.enums;

import lombok.Getter;

/**
 * 登录类型：前台、后台
 *
 * @author DJun
 */
@Getter
public enum LoginType {
    /**  App端 */
    App("0","app"),
    /** admin端 */
    admin("1","admin");

    private final String code;
    private final String info;

    LoginType(String code,String info){
        this.code = code;
        this.info = info;
    }
}
