package com.fun.project.app.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author DJun
 * @date 2019/11/25
 */
@Data
@ApiModel("通用用户实体DTO")
public class UserDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty(value = "登录账号", required = true)
    @Size(min = 0, max = 30, message = "登录账号长度不能超过30个字符")
    private String loginName;

    @ApiModelProperty("用户昵称")
    @Size(min = 0, max = 30, message = "用户昵称长度不能超过30个字符")
    private String username;

    @ApiModelProperty(value = "u号",
            notes = "u号相当于微信号，只能修改一次，第一次由系统分配,作为用户的第二账号，系统亦可将此号作为业务扩展账号")
    @Size(min = 0, max = 30, message = "u号长度不能超过30个字符")
    private String uAccount;

    @ApiModelProperty("邮箱")
    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    private String email;

    @ApiModelProperty(value = "用户性别", notes = "0-男 1-女 2-未知")
    private String sex;

    @ApiModelProperty(value = "用户密码", notes = "该项参数使用密文", required = true)
    @Size(min = 0, max = 30, message = "密码长度不能超过30个字符")
    private String password;

    @ApiModelProperty(value = "手机号码")
    @Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
    private String telephone;

    @ApiModelProperty(value = "密码加密盐", notes = "长度6-8位",required = true)
    @Size(min = 6, max = 8, message = "盐的长度在6-8位")
    private String salt;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

}
