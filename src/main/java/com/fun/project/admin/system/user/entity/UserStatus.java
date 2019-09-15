package com.fun.project.admin.system.user.entity;

import lombok.Getter;

/**
 * created by DJun on 2019/9/14 9:45
 * desc: 用户状态
 */
@Getter
public enum UserStatus {
    OK("1", "正常"), DISABLE("0", "禁用"), DELETED("2", "删除");

    private final String code;
    private final String info;

    UserStatus(String code, String info) {
        this.code = code;
        this.info = info;
    }

}
