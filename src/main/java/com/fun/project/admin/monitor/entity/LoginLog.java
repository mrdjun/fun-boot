package com.fun.project.admin.monitor.entity;

import com.fun.framework.web.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 登录日志实体类
 * @author DJun
 * @date 2019/9/13 12:25
 */
@Getter
@Setter
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
