package com.fun.project.admin.monitor.log.entity;

import com.fun.framework.web.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * created by DJun on 2019/9/13 12:25
 * desc: 登录日志实体类
 */
@Getter
@Setter
@ToString
public class LoginLog extends BaseEntity {
    /** 主键 */
    private Long infoId;
    /** 登录账号 */
    private String loginName;
    /** 登录地点 */
    private String loginLocation;
    /** 登录ip */
    private String ipaddr;
    /** 操作系统 */
    private String os;
    /** 登录浏览器 */
    private String browser;
    /** 消息 */
    private String msg;
}
