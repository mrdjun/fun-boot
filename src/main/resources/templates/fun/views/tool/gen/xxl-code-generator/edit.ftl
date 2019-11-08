<!DOCTYPE html>
<html lang="zh" xmlns:th="http://www.thymeleaf.org" >
<head>
    <th:block th:include="include :: header('修改${classInfo.classComment}')" />
</head>
<body class="white-bg">
<div class="wrapper wrapper-content animated fadeInRight ibox-content">
    <form class="form-horizontal m" id="form-${classInfo.className?uncap_first}-edit" th:object="$...{${classInfo.className?uncap_first}}">
        <input name="${classInfo.conversionPrimaryKey}" th:field="*...{${classInfo.conversionPrimaryKey}}" type="hidden">

        <#list classInfo.fieldList as fieldItem >
            <#if fieldItem.fieldName='status'>
                <div class="form-group">
                    <label class="col-sm-3 control-label">${fieldItem.fieldComment}</label>
                    <div class="col-sm-8">
                        <div class="radio-box">
                            <input type="radio" name="status" value="">
                            <label th:for="status" th:text="未知"></label>
                        </div>
                        <span class="help-block m-b-none"><i class="fa fa-info-circle"></i> 代码生成请选择字典属性</span>
                    </div>
                </div>
                <#elseif fieldItem.fieldName='createTime'>
                <#elseif fieldItem.fieldName='createBy'>
                <#elseif fieldItem.fieldName='updateTime'>
                <#elseif fieldItem.fieldName='updateBy'>
                <#elseif fieldItem.fieldName='remark'>
                <div class="form-group">
                    <label class="col-sm-3 control-label">${fieldItem.fieldComment}：</label>
                    <div class="col-sm-8">
                        <textarea name="${fieldItem.fieldName}" class="form-control" required>[[*{${fieldItem.fieldName}}]]</textarea>
                    </div>
                </div>
                <#else>
                    <div class="form-group">
                    <label class="col-sm-3 control-label">${fieldItem.fieldComment}</label>
                    <div class="col-sm-8">
                        <input name="typeId" th:field="*...{${fieldItem.fieldName}}" class="form-control" type="text">
                    </div>
                </div>
            </#if>
        </#list>
    </form>
</div>
<th:block th:include="include :: footer" />
<script type="text/javascript">
    var prefix = ctx + "admin/system/${classInfo.className?uncap_first}";

    $("#form-${classInfo.className?uncap_first}-edit").validate({
        focusCleanup: true
    });

    function submitHandler() {
        if ($.validate.form()) {
            $.operate.save(prefix + "/edit", $('#form-${classInfo.className?uncap_first}-edit').serialize());
        }
    };

    function doSubmit(index, layero){
        var body = layer.getChildFrame('body', index);
        $("#treeId").val(body.find('#treeId').val());
        $("#treeName").val(body.find('#treeName').val());
        layer.close(index);
    }
</script>
</body>
</html>