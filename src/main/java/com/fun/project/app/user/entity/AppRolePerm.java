package com.fun.project.app.user.entity;

import lombok.ToString;
import lombok.Getter;
import lombok.Setter;
import com.fun.framework.web.entity.BaseEntity;

/**
 * 角色权限
 *
 * @author DJun
 * @date 2019-12-02
 */
@Getter
@Setter
@ToString
public class AppRolePerm extends BaseEntity {
    private static final long serialVersionUID=1L;

    /** 角色ID */
    private Long roleId;

    /** 字符串ID */
    private Long permId;
}