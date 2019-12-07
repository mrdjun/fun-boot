package com.fun.project.admin.system.entity;

import com.fun.framework.annotation.Excel;
import com.fun.framework.web.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 岗位表
 *
 * @author DJun
 */
@Getter
@Setter
@ToString
public class Post extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Excel(name = "岗位序号", cellType = Excel.ColumnType.NUMERIC)
    private Long postId;

    @Excel(name = "岗位编码")
    private String postCode;

    @Excel(name = "岗位名称")
    private String postName;

    @Excel(name = "岗位排序")
    private String postSort;

    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /**  用户是否存在此岗位标识 默认不存在 */
    private boolean flag = false;

    @NotBlank(message = "岗位编码不能为空")
    @Size(min = 0, max = 64, message = "岗位编码长度不能超过64个字符")
    public String getPostCode()
    {
        return postCode;
    }

    @NotBlank(message = "岗位名称不能为空")
    @Size(min = 0, max = 50, message = "岗位名称长度不能超过50个字符")
    public String getPostName()
    {
        return postName;
    }

    @NotBlank(message = "显示顺序不能为空")
    public String getPostSort()
    {
        return postSort;
    }
}
