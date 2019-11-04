package com.fun.project.admin.system.entity;

import com.fun.framework.web.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 部门表 sys_dept
 *
 * @author fun
 */
@Setter
@Getter
@ToString
public class Dept extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 父部门ID
     */
    private Long parentId;

    /**
     * 祖级列表
     */
    private String ancestors;

    @NotBlank(message = "部门名称不能为空")
    @Size(min = 0, max = 30, message = "部门名称长度不能超过30个字符")
    private String deptName;

    /**
     * 显示顺序
     */
    @NotBlank(message = "显示顺序不能为空")
    private String orderNum;

    /**
     * 负责人
     */
    private String leader;


    @Size(min = 0, max = 11, message = "联系电话长度不能超过11个字符")
    private String phone;

    /**
     * 邮箱
     */
    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    private String email;

    /**
     * 删除标志（1代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 父部门名称
     */
    private String parentName;
}
