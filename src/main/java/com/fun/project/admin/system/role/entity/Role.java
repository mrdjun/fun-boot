package com.fun.project.admin.system.role.entity;

import com.fun.framework.web.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * created by DJun on 2019/9/14 12:51
 * desc:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Role extends BaseEntity {
    private Long roleId;
    private String roleName;
    private String roleKey;
    private String roleSort;
    private String delFlag;
    private String createBy;
    private String updateBy;

    /** 菜单组 */
    private Long[] menuIds;

    /** 用户是否存在此角色标识 默认不存在 */
    private boolean flag = false;

}
