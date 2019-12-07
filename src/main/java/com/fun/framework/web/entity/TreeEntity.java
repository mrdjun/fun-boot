package com.fun.framework.web.entity;


import lombok.Getter;
import lombok.Setter;

/**
 * Tree基类
 *
 * @author DJun
 */
@Getter
@Setter
public class TreeEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 父菜单名称 */
    private String parentName;

    /** 父菜单ID */
    private Long parentId;

    /** 显示顺序 */
    private Integer orderNum;
}
