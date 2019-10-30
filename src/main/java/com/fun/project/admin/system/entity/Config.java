package com.fun.project.admin.system.entity;

import com.fun.framework.web.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 参数配置表
 *
 * @author u-fun
 * @date 2019/10/30
 */
@Getter
@Setter
@ToString
public class Config extends BaseEntity {

    /**
     * 参数主键
     */
    private Long configId;


    /**
     * 参数名称
     */
    private String configName;


    /**
     * 参数键名
     */
    private String configKey;


    /**
     * 参数键值
     */
    private String configValue;


    /**
     * 系统内置（Y是 N否）
     */
    private String configType;

}