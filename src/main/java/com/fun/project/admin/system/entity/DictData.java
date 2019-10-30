package com.fun.project.admin.system.entity;

import com.fun.framework.web.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 字典数据表
 *
 * @author u-fun
 * @date 2019/10/30
 */
@Getter
@Setter
@ToString
public class DictData extends BaseEntity {

    /**
     * 字典编码
     */
    private Long dictCode;


    /**
     * 字典排序
     */
    private Long dictSort;


    /**
     * 字典标签
     */
    private String dictLabel;


    /**
     * 字典键值
     */
    private String dictValue;


    /**
     * 字典类型
     */
    private String dictType;


    /**
     * 样式属性（其他样式扩展）
     */
    private String cssClass;


    /**
     * 表格回显样式
     */
    private String listClass;


    /**
     * 是否默认（Y是 N否）
     */
    private String isDefault;

}