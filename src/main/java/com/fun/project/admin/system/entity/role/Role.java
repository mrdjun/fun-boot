package com.fun.project.admin.system.entity.role;

import com.fun.framework.web.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author DJun
 * @date 2019/9/14 12:51
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Role extends BaseEntity {

    private Long roleId;

    @NotBlank(message = "角色名称不能为空")
    @Size(min = 0, max = 30, message = "角色名称长度不能超过30个字符")
    private String roleName;

    @NotBlank(message = "权限字符不能为空")
    @Size(min = 0, max = 100, message = "权限字符长度不能超过100个字符")
    private String roleKey;

    /** 角色排序 */
    @NotBlank(message = "显示顺序不能为空")
    private String roleSort;

    /** 删除标志（1正常 2已删除） */
    private String delFlag;

    /** 数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限） */
    private String dataScope;

    /** 用户是否存在此角色标识 默认不存在 */
    private boolean flag = false;

    /** 菜单组 */
    private Long[] menuIds;

    /** 部门组（数据权限） */
    private Long[] deptIds;
}
