package com.fun.framework.web.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;


/**
 * Entity 基类
 * @author DJun
 */
@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long createTime;
    private Long updateTime;
    private String status;
    /** 搜索值 */
    private String searchValue;
    /** 备注 */
    private String remark;
    /** 请求参数 */
    private Map<String, Object> params;
}
