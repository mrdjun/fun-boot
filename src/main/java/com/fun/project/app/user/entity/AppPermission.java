package com.fun.project.app.user.entity;

import lombok.ToString;
import lombok.Getter;
import lombok.Setter;
import com.fun.framework.web.entity.BaseEntity;

import lombok.ToString;
import lombok.Getter;
import lombok.Setter;
import com.fun.framework.annotation.Excel;
import com.fun.framework.web.entity.BaseEntity;

/**
 * 权限
 *
 * @author DJun
 * @date 2019-11-27
 */
@Getter
@Setter
@ToString
public class AppPermission extends BaseEntity {
    private static final long serialVersionUID=1L;

    /** 权限ID */
    private Long permId;

    /** 权限字符串 */
    private String perm;

    /** 权限名称 */
    private String permName;

    /** 角色状态0-禁用1-正常 */
    private String status;

    private Long roleId;
}