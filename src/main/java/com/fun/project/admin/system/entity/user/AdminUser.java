package com.fun.project.admin.system.entity.user;

import com.fun.framework.annotation.Excel;
import com.fun.framework.annotation.Excel.ColumnType;
import com.fun.framework.annotation.Excel.Type;
import com.fun.framework.annotation.Excels;
import com.fun.framework.web.entity.BaseEntity;
import com.fun.project.admin.system.entity.Dept;
import com.fun.project.admin.system.entity.role.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 管理员实体类
 *
 * @author DJun
 * @date 2019/9/13 23:17
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdminUser extends BaseEntity {
    @Excel(name = "用户序号", cellType = ColumnType.NUMERIC, prompt = "用户编号")
    private Long userId;

    @Excel(name = "部门编号", type = Type.IMPORT)
    private Long deptId;

    private Long roleId;

    @Excel(name = "登录账号")
    private String loginName;

    @Excel(name = "用户名称")
    private String username;

    @Excel(name = "用户邮箱")
    private String email;

    @Excel(name = "手机号码")
    private String telephone;

    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    private String sex;

    /** 头像地址 */
    private String avatar;

    private String password;

    /** 加密盐 */
    private String salt;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    @Excel(name = "帐号状态", readConverterExp = "0=停用,1=正常")
    private String status;

    @Excel(name = "最后登陆IP", type = Type.EXPORT)
    private String loginIp;

    @Excel(name = "最后登陆时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.EXPORT)
    private Long loginDate;

    private List<Role> roles;
    /**
     * 角色组
     */
    private Long[] roleIds;

    /**
     * 部门对象
     */
    @Excels({
            @Excel(name = "部门名称", targetAttr = "deptName", type = Type.EXPORT),
            @Excel(name = "部门负责人", targetAttr = "leader", type = Type.EXPORT)
    })
    private Dept dept;

    /**
     * 岗位组
     */
    private Long[] postIds;

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

    @NotBlank(message = "登录账号不能为空" )
    @Size(min = 0, max = 30, message = "登录账号长度不能超过30个字符" )
    public String getLoginName() {
        return loginName;
    }

    @Size(min = 0, max = 30, message = "用户昵称长度不能超过30个字符" )
    public String getUsername() {
        return username;
    }

    @Email(message = "邮箱格式不正确" )
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符" )
    public String getEmail() {
        return email;
    }

    @Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符" )
    public String getTelephone() {
        return telephone;
    }

    public Dept getDept() {
        if (dept == null) {
            dept = new Dept();
        }
        return dept;
    }
}
