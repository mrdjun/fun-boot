package com.fun.project.admin.system.entity.user;

import com.fun.framework.web.entity.BaseEntity;
import com.fun.project.admin.system.entity.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 管理员实体类
 * @author DJun
 * @date 2019/9/13 23:17
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdminUser extends BaseEntity {
    private Long userId;

    @NotBlank(message = "登录账号不能为空")
    @Size(max = 30, message = "登录账号长度不能超过30个字符")
    private String loginName;

    @Size(max = 30, message = "用户昵称长度不能超过30个字符")
    private String username;

    @Email(message = "邮箱格式不正确")
    @Size(max = 30, message = "邮箱长度不能超过50个字符")
    private String email;

    @Size(max = 11, message = "手机号码长度不能超过11个字符")
    private String telephone;
    /**
     * 0-男1-女2-未知
     */
    private String sex;
    /**
     * 头像地址
     */
    private String avatar;

    private String password;
    /**
     * 加密盐
     */
    private String salt;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;
    /**
     * 最后一次登录ip
     */
    private String loginIp;

    private Long loginDate;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 修改者
     */
    private String updateBy;

    private List<Role> roles;
    /**
     * 角色组
     */
    private Long[] roleIds;

    /**
     * 生成随机盐
     */
    public void randomSalt() {
        // 一个Byte占两个字节，此处生成的3字节，字符串长度为6
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        String hex = secureRandom.nextBytes(3).toHex();
        setSalt(hex);
    }

    public boolean isAdmin() {
        return isAdmin(this.userId);
    }

    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }

    public Long getId() {
        return userId;
    }
}
