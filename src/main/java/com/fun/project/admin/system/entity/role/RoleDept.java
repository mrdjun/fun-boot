package com.fun.project.admin.system.entity.role;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 部门角色
 *
 * @author DJun
 * @date 2019/10/31
 */
@Getter
@Setter
@ToString
public class RoleDept {
    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 部门ID
     */
    private Long deptId;
}
