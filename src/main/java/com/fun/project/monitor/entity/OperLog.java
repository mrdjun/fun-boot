package com.fun.project.monitor.entity;


import com.fun.framework.web.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * created by DJun on 2019/9/9 20:57
 * desc: 日志实体
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OperLog extends BaseEntity {
    /** 日志主键 */
    private Long operId;
    /** 操作名称 */
    private String operation;
    /** 操作者IP */
    private String operIp;
    /** 操作者地点*/
    private String operLocation;
    /** 状态0正常 1异常 */
    private Integer status;
    /** 错误消息 */
    private String errorMsg;
    /** 操作用户 */
    private String username;
    /** 操作耗时（毫秒) */
    private Long time;
    /** 操作方法 */
    private String method;
    /** 方法参数 */
    private String params;

}
