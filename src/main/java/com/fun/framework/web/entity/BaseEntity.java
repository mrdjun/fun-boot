package com.fun.framework.web.entity;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;


/**
 * Entity 基类
 *
 * @author DJun
 */
@Getter
@Setter
@ToString
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 时间戳 */
    private Long createTime;
    private Long updateTime;
    /** 0-禁用 1-正常 */
    private String status;
    /** 创建者 */
    private String createBy;
    /** 更新者 */
    private String updateBy;
    /** 搜索值 */
    private String searchValue;
    /** 备注 */
    private String remark;
    /** 请求参数 */
    private Map<String, Object> params;

    public Map<String, Object> getParams() {
        if (params == null) {
            params = Maps.newHashMap();
        }
        return params;
    }
}
