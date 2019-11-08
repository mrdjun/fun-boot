<!DOCTYPE html>
<html lang="zh" xmlns:th="http://www.thymeleaf.org" >
<head>
    <th:block th:include="include :: header('新增${classInfo.classComment}')" />
   
</head>
<body class="white-bg">
<div class="wrapper wrapper-content animated fadeInRight ibox-content">
    <form class="form-horizontal m" id="form-${classInfo.className?uncap_first}-add">
        <#list classInfo.fieldList as fieldItem >
            <#if fieldItem.fieldName='status'>
                <div class="form-group">
                    <label class="col-sm-3 control-label">${fieldItem.fieldComment}：</label>
                    <div class="col-sm-8">
                        <select name="${fieldItem.fieldName}" class="form-control m-b" required>
                            <option value="">所有</option>
                        </select>
                        <span class="help-block m-b-none"><i class="fa fa-info-circle"></i> 代码生成请选择字典属性</span>
                    </div>
                </div>

                <#else>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">${fieldItem.fieldComment}：</label>
                        <div class="col-sm-8">
                            <textarea name="${fieldItem.fieldName}" class="form-control" required></textarea>
                        </div>
                    </div>
            </#if>

        </#list>

    </form>
</div>
<th:block th:include="include :: footer" />

<script type="text/javascript">
    var prefix = ctx + "admin/system/${classInfo.className?uncap_first}"
    $("#form-${classInfo.className?uncap_first}-add").validate({
        focusCleanup: true
    });

    function submitHandler() {
        if ($.validate.form()) {
            $.operate.save(prefix + "/add", $('#form-${classInfo.className?uncap_first}-add').serialize());
        }
    }

    function doSubmit(index, layero){
        var body = layer.getChildFrame('body', index);
        $("#treeId").val(body.find('#treeId').val());
        $("#treeName").val(body.find('#treeName').val());
        layer.close(index);
    }
</script>
</body>
</html>