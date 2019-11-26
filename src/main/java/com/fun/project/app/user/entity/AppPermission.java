package com.fun.project.app.user.entity;

import lombok.ToString;
import lombok.Getter;
import lombok.Setter;
import com.fun.framework.web.entity.BaseEntity;

/**
 * 权限
 *
 * @author MrDJun
 * @date 2019-11-24
 */
@Getter
@Setter
@ToString
public class AppPermission extends BaseEntity {
    private static final long serialVersionUID=1L;

    private Long permsId;

    private String perms;

    private String permsName;

}
