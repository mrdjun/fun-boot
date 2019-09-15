package com.fun.project.admin.system.user.entity;

import lombok.Data;

/**
 * created by DJun on 2019/9/14 9:45
 * desc: 用户和角色关联 sys_user_role
 */
@Data
public class UserRole {
    /** 用户ID */
    private Long userId;

    /** 角色ID */
    private Long roleId;
}
