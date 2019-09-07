package com.fun.framework.web;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * created by DJun on 2019/9/7 15:46
 * desc: Entity 基类
 */
@Getter
@Setter
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String createTime;
    private String updateTime;
    private String status;
    /** 搜索值 */
    private String searchValue;
    /** 备注 */
    private String remark;
}
