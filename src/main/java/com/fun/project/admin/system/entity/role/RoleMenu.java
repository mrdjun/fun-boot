package com.fun.project.admin.system.entity.role;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 角色菜单
 *
 * @author DJun
 * @date 2019/9/14 12:52
 */
@Getter
@Setter
@ToString
public class RoleMenu {

    /** 角色ID */
    private Long roleId;

    /** 菜单ID */
    private Long menuId;
}
