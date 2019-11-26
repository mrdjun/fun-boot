package com.fun.project.app.user.dto;

import com.fun.framework.web.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author DJun
 * @date 2019/11/25
 */
@ApiModel("当前用户的角色及权限实体")
@EqualsAndHashCode(callSuper = true)
@Data
public class UserPerms extends BaseEntity{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("权限ID")
    private Long permsId;

    @ApiModelProperty("角色字符串")
    private String roleKey;

    @ApiModelProperty("权限字符串")
    private String perms;

    @ApiModelProperty("权限名称")
    private String permsName;
}
