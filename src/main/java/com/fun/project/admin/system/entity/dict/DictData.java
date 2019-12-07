package com.fun.project.admin.system.entity.dict;

import com.fun.common.constant.Constants;
import com.fun.framework.annotation.Excel;
import com.fun.framework.annotation.Excel.ColumnType;
import com.fun.framework.web.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 字典数据表
 *
 * @author DJun
 * @date 2019/10/30
 */
@Getter
@Setter
@ToString
public class DictData extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Excel(name = "字典编码", cellType = ColumnType.NUMERIC)
    private Long dictCode;

    @Excel(name = "字典排序", cellType = ColumnType.NUMERIC)
    private Long dictSort;

    @Excel(name = "字典标签")
    private String dictLabel;

    @Excel(name = "字典键值")
    private String dictValue;

    @Excel(name = "字典类型")
    private String dictType;

    /** 样式属性（其他样式扩展） */
    @Excel(name = "字典样式")
    private String cssClass;

    private String listClass;

    @Excel(name = "是否默认", readConverterExp = "Y=是,N=否")
    private String isDefault;

    public boolean getDefault() {
        return Constants.YES.equals(this.isDefault);
    }

    @NotBlank(message = "字典标签不能为空")
    @Size(min = 0, max = 100, message = "字典标签长度不能超过100个字符")
    public String getDictLabel()
    {
        return dictLabel;
    }

    public void setDictLabel(String dictLabel)
    {
        this.dictLabel = dictLabel;
    }

    @NotBlank(message = "字典键值不能为空")
    @Size(min = 0, max = 100, message = "字典键值长度不能超过100个字符")
    public String getDictValue()
    {
        return dictValue;
    }

    public void setDictValue(String dictValue)
    {
        this.dictValue = dictValue;
    }

    @NotBlank(message = "字典类型不能为空")
    @Size(min = 0, max = 100, message = "字典类型长度不能超过100个字符")
    public String getDictType()
    {
        return dictType;
    }

    public void setDictType(String dictType)
    {
        this.dictType = dictType;
    }

    @Size(min = 0, max = 100, message = "样式属性长度不能超过100个字符")
    public String getCssClass()
    {
        return cssClass;
    }


}