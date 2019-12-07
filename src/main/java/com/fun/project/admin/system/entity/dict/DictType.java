package com.fun.project.admin.system.entity.dict;

import com.fun.framework.annotation.Excel;
import com.fun.framework.annotation.Excel.ColumnType;
import com.fun.framework.web.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 字典类型表
 *
 * @author DJun
 * @date 2019/10/30
 */
@Getter
@Setter
@ToString
public class DictType extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Excel(name = "字典主键" , cellType = ColumnType.NUMERIC)
    private Long dictId;

    @Excel(name = "字典名称" )
    private String dictName;

    @Excel(name = "字典类型 " )
    private String dictType;

    @NotBlank(message = "字典名称不能为空" )
    @Size(min = 0, max = 100, message = "字典类型名称长度不能超过100个字符" )
    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    @NotBlank(message = "字典类型不能为空" )
    @Size(min = 0, max = 100, message = "字典类型类型长度不能超过100个字符" )
    public String getDictType() {
        return dictType;
    }
}