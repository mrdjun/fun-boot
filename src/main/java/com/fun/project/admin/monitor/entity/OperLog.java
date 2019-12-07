package com.fun.project.admin.monitor.entity;

import com.fun.framework.web.entity.BaseEntity;
import lombok.*;

/**
 * 操作日志
 *
 * @author DJun
 * @date 2019/9/9 20:57
 */
@Getter
@Setter
@ToString
public class OperLog extends BaseEntity {
    /** 日志主键 */
    private Long operId;

    /** 操作名称 */
    private String operName;

    /** 操作者IP */
    private String operIp;

    /** 操作者地点*/
    private String operLocation;

    /** 错误消息 */
    private String errorMsg;

    /** 操作用户 */
    private String loginName;

    /** 操作耗时（毫秒) */
    private Long time;

    /** 操作方法 */
    private String method;

    /** 方法参数 */
    private String operParam;
}
