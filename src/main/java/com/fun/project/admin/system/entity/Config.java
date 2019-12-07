package com.fun.project.admin.system.entity;

import com.fun.framework.annotation.Excel;
import com.fun.framework.web.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 参数配置表
 *
 * @author DJun
 * @date 2019/10/30
 */
@Getter
@Setter
@ToString
public class Config extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Excel(name = "参数主键", cellType = Excel.ColumnType.NUMERIC)
    private Long configId;

    @Excel(name = "参数名称")
    private String configName;

    @Excel(name = "参数键名")
    private String configKey;

    @Excel(name = "参数键值")
    private String configValue;

    @Excel(name = "系统内置", readConverterExp = "Y=是,N=否")
    private String configType;

    @NotBlank(message = "参数名称不能为空")
    @Size(min = 0, max = 100, message = "参数名称不能超过100个字符")
    public String getConfigName()
    {
        return configName;
    }

    @NotBlank(message = "参数键名长度不能为空")
    @Size(min = 0, max = 100, message = "参数键名长度不能超过100个字符")
    public String getConfigKey()
    {
        return configKey;
    }

    @NotBlank(message = "参数键值不能为空")
    @Size(min = 0, max = 500, message = "参数键值长度不能超过500个字符")
    public String getConfigValue()
    {
        return configValue;
    }
}