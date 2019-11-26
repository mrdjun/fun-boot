package com.fun.project.app.user.entity;

import com.fun.framework.web.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author DJun
 * @date 2019/11/24
 */
@Getter
@Setter
@ToString
public class AppRole extends BaseEntity {
    private static final long serialVersionUID=1L;

    private Long roleId;

    private String roleKey;

    private String roleName;

}