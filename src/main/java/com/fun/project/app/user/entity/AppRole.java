package com.fun.project.app.user.entity;

import com.fun.framework.annotation.Excel;
import com.fun.framework.web.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author DJun
 * @date 2019/11/24
 */
@Getter
@Setter
@ToString
public class AppRole extends BaseEntity {
    private static final long serialVersionUID=1L;

    @Excel(name="角色ID")
    private Long roleId;

    @Excel(name="角色字符串")
    @Size(min = 0, max = 100, message = "权限字符长度不能超过100个字符")
    private String roleKey;

    @Excel(name="角色名称")
    @Size(min = 0, max = 30, message = "角色名称长度不能超过30个字符")
    private String roleName;

    /** 删除标记：1-正常2-已删除 */
    private String delFlag;

    @Excel(name="备注")
    private String remark;

    /** 用户是否存在此角色标识 默认不存在 */
    private boolean flag = false;

    /** 权限组 */
    private Long[] permIds;
}