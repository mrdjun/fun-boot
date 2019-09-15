package com.fun.project.admin.system.menu.entity;

import com.fun.framework.web.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * created by DJun on 2019/9/14 9:41
 * desc:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Menu extends BaseEntity {
    private Long menuId;
    private String menuName;
    private String parentName;
    private Long parentId;
    private Integer orderNum;
    private String url;
    /** 打开方式：menuItem页签 menuBlank新窗口 */
    private String target;
    /** 类型:0目录,1菜单,2按钮 */
    private String menuType;
    /** 菜单状态:0显示,1隐藏 */
    private String visible;
    /** 权限字符串 */
    private String perms;
    private String icon;
    private String createBy;
    private String updateBy;

    /** 子菜单 */
    private List<Menu> children = new ArrayList<>();
}
