package com.fun.project.admin.system.entity;

import com.fun.common.constant.Constants;
import com.fun.framework.web.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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


    @NotBlank(message = "字典标签不能为空")
    @Size(min = 0, max = 100, message = "字典标签长度不能超过100个字符")
    private String dictLabel;


    @NotBlank(message = "字典键值不能为空")
    @Size(min = 0, max = 100, message = "字典键值长度不能超过100个字符")
    private String dictValue;


    @NotBlank(message = "字典类型不能为空")
    @Size(min = 0, max = 100, message = "字典类型长度不能超过100个字符")
    private String dictType;


    /**
     * 样式属性（其他样式扩展）
     */
    @Size(min = 0, max = 100, message = "样式属性长度不能超过100个字符")
    private String cssClass;


    /**
     * 表格回显样式
     */
    private String listClass;


    /**
     * 是否默认（Y是 N否）
     */
    private String isDefault;

    public boolean getDefault() {
        return Constants.YES.equals(this.isDefault);
    }

}