package com.fun.project.app.user.entity;

import com.fun.framework.annotation.Excel;
import com.fun.framework.web.entity.BaseEntity;
import lombok.*;

/**
 * @author DJun
 * @date 2019/9/7 15:35
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AppUser extends BaseEntity {

    private Long userId;

    @Excel(name = "登录账号")
    private String loginName;

    @Excel(name = "用户名")
    private String username;

    @Excel(name = "u号")
    private String uAccount;

    @Excel(name = "用户等级")
    private Integer userLevel;

    @Excel(name = "用户邮箱")
    private String email;

    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    private String sex;

    /** 用户密码 */
    private String password;

    /** 加密盐 */
    private String salt;

    /** 头像路径 */
    private String avatar;

    @Excel(name = "电话号码")
    private String telephone;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    /** 最后登陆IP */
    private String loginIp;

    /** 最后登陆时间 */
    private Long loginDate;

    /** 开放id */
    private String openId;

    /** 0-可修改u号1-不可修改 */
    private String isLock;

    @Excel(name = "信誉度")
    private Integer honor;

    /** 经验 */
    private Integer exp;

    @Excel(name = "粉丝量")
    private Long fansNum;

    @Excel(name = "关注量")
    private Long followNum;

    /** 个人信息公开程度 */
    private String oid;

    /** 健康度 */
    private Integer health;

    /** 禁用账号到期时间 */
    private Long banTime;

    @Excel(name = "是否认证", readConverterExp = "0=未认证,1=已认证")
    private String isVerify;

    public AppUser(String loginName,String username,String password){
        this.loginName = loginName;
        this.username = username;
        this.password = password;
    }

    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 当前用户角色
     */
    private String roleKey;
    /**
     * 当前用户角色名称
     */
    private String roleName;
}
