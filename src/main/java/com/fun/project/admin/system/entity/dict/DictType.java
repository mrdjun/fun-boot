package com.fun.project.admin.system.entity.dict;

import com.fun.framework.web.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 字典类型表
 *
 * @author u-fun
 * @date 2019/10/30
 */
@Getter
@Setter
@ToString
public class DictType extends BaseEntity {

    /**
     * 字典主键
     */
    private Long dictId;

    /**
     * 字典名称
     */
    private String dictName;


    /**
     * 字典类型
     */
    private String dictType;

}