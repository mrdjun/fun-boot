package com.fun.project.admin.system.entity;

<#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
    <#list classInfo.fieldList as fieldItem >
        <#if fieldItem.fieldClass == "Date">
            <#assign importDdate = true />
        </#if>
    </#list>
</#if>
import com.fun.framework.web.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
<#if importDdate?? && importDdate>
import java.util.Date;
</#if>

/**
 * ${classInfo.classComment}
 *
 * @author u-fun
 * @date ${.now?string('yyyy/MM/dd')}
 */
@Getter
@Setter
@ToString
public class ${classInfo.className} extends BaseEntity {
<#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
    <#assign index=0 />
    <#list classInfo.fieldList as fieldItem >
        /**
        * ${fieldItem.fieldComment}
        */

        <#if fieldItem.fieldClass=='int' && index== 0>
            private Long ${fieldItem.fieldName};
            <#assign index=1 />
        <#elseif fieldItem.fieldName=='create_by' || fieldItem.fieldName=='createBy'>
        <#elseif fieldItem.fieldName=='create_time' || fieldItem.fieldName=='createTime'>
        <#elseif fieldItem.fieldName=='update_by' || fieldItem.fieldName=='updateBy'>
        <#elseif fieldItem.fieldName=='update_time' || fieldItem.fieldName=='updateTime'>
        <#elseif fieldItem.fieldName=='remark'>
        <#elseif fieldItem.fieldName=='status'>
        <#elseif fieldItem.fieldClass=='int' && index==1>
            private Integer ${fieldItem.fieldName};
        <#elseif fieldItem.fieldClass=='long'>
            private Long ${fieldItem.fieldName};
        <#else>
            private ${fieldItem.fieldClass} ${fieldItem.fieldName};
        </#if>

    </#list>
</#if>
}