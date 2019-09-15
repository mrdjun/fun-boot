package com.fun.project.app.user.entity;

import com.fun.framework.web.entity.BaseEntity;
import lombok.*;

/**
 * created by DJun on 2019/9/7 15:35
 * desc: ums_user 实体类
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AppUser extends BaseEntity {
    private Long userId;

    /** 登录账号 */
    private String loginName;

    /** 用户名 */
    private String username;

    /** u号 */
    private String uAccount;

    /** 用户类型（00系统用户） */
    private String userType;

    /** 用户等级 */
    private Integer userLevel;

    /** 用户等级 */
    private String email;

    /** 用户性别（0男 1女 2未知） */
    private String sex;

    /** 用户性别 */
    private String password;

    /** 加密盐 */
    private String salt;

    /** 头像路径 */
    private String avatar;

    /** 头像路径 */
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

    /** 0-禁用1-正常 */
    private String status;

    /** 信誉度 */
    private Integer honor;

    /** 经验 */
    private Integer exp;

    /** 粉丝量 */
    private Long fansNum;

    /** 关注量 */
    private Long followNum;

    /** 个人信息公开程度:0-自己可见，1-同班，2-同系，3-同校，4-全部 */
    private String oid;

    /** 健康度 */
    private Integer health;

    /** 禁用账号到期时间 */
    private Long banTime;

    /** 1-已认证，0-未认证 */
    private String isVerify;
}
