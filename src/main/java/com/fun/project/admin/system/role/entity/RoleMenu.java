package com.fun.project.admin.system.role.entity;

import lombok.Data;

/**
 * created by DJun on 2019/9/14 12:52
 * desc: 角色与菜单 关联
 */
@Data
public class RoleMenu {
    /** 角色id */
    private Long roleId;
    /** 菜单id */
    private Long menuId;
}
