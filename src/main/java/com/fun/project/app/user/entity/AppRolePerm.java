package com.fun.project.app.user.entity;

import com.fun.framework.web.entity.BaseEntity;
import lombok.ToString;
import lombok.Getter;
import lombok.Setter;

/**
 * @author DJun
 * @date 2019/11/24
 */
@Getter
@Setter
@ToString
public class AppRolePerm extends BaseEntity {
    private static final long serialVersionUID=1L;

    private Long roleId;

    private Long permsId;
}
