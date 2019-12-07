package com.fun.project.admin.system.entity.user;

import lombok.Getter;

/**
 *  用户状态
 *
 * @author DJun
 */
@Getter
public enum UserStatus {

    /** 禁用 */
    DISABLE("0", "禁用"),

    /** 正常 */
    OK("1", "正常"),

    /** 删除 */
    DELETED("2", "删除");

    private final String code;
    private final String info;

    UserStatus(String code, String info) {
        this.code = code;
        this.info = info;
    }

}
