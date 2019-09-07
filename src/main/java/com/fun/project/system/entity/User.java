package com.fun.project.system.entity;

import com.fun.framework.web.BaseEntity;
import lombok.*;

/**
 * created by DJun on 2019/9/7 15:35
 * desc: ums_user 实体类
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User extends BaseEntity {
    private Long userId;
    private String loginName;
    private String username;
    private String userType;
    private Integer userLevel;
    private String email;
    private String sex;
    private String password;
    private String salt;
    private String avatar;
    private String telephone;
    private String delFlag;
    private String loginIp;
    private String loginDate;
}
