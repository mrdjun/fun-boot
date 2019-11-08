<!DOCTYPE html>
<html lang="zh" xmlns:th="http://www.thymeleaf.org" xmlns:shiro="http://www.pollix.at/thymeleaf/shiro">
<head>
    <th:block th:include="include :: header('${classInfo.classComment}列表')"/>
    <#list classInfo.fieldList as fieldItem >
        <#if fieldItem.fieldName=='createTime'>
            <#assign createTimeFlag = 1>
            <th:block th:include="include :: datetimepicker-css" />
        </#if>
    </#list>
</head>
<body class="gray-bg">
<div class="container-div">
    <div class="row">
        <div class="col-sm-12 search-collapse">
            <form id="formId">
                <div class="select-list">
                    <ul>
                        <#list classInfo.fieldList as fieldItem >
                            <#if createTimeFlag == 1>
                                <li class="select-time">
                                    <label>创建时间： </label>
                                    <input type="text" class="time-input" id="startTime" placeholder="开始时间"
                                           name="params[beginTime]"/>
                                    <span>-</span>
                                    <input type="text" class="time-input" id="endTime" placeholder="结束时间"
                                           name="params[endTime]"/>
                                </li>
                                <#elseif fieldItem.fieldName==classInfo.conversionPrimaryKey>
                                <#elseif fieldItem.fieldName=='status'>
                                    <li>
                                        ${fieldItem.fieldComment}：<select name="status" th:with="type=$...{@dict.getType('sys_normal_disable')}">
                                            <option value="">所有</option>
                                            <option th:each="dict : $...{type}" th:text="$...{dict.dictLabel}" th:value="$...{dict.dictValue}"></option>
                                        </select>
                                    </li>
                                <#else>
                                <li>
                                    <p>${fieldItem.fieldComment}：</p>
                                    <input type="text" name="${fieldItem.fieldName}"/>
                                </li>

                            </#if>
                        </#list>

                        <li>
                            <a class="btn btn-primary btn-rounded btn-sm" onclick="$.table.search()"><i
                                        class="fa fa-search"></i>&nbsp;搜索</a>
                            <a class="btn btn-warning btn-rounded btn-sm" onclick="$.form.reset()"><i
                                        class="fa fa-refresh"></i>&nbsp;重置</a>
                        </li>
                    </ul>
                </div>
            </form>
        </div>

        <div class="btn-group-sm" id="toolbar" role="group">
            <a class="btn btn-success" onclick="$.operate.add()"
               shiro:hasPermission="system:${classInfo.className?uncap_first}:add">
                <i class="fa fa-plus"></i> 添加
            </a>
            <a class="btn btn-primary single disabled" onclick="$.operate.edit()"
               shiro:hasPermission="system:${classInfo.className?uncap_first}:edit">
                <i class="fa fa-edit"></i> 修改
            </a>
            <a class="btn btn-danger multiple disabled" onclick="$.operate.removeAll()"
               shiro:hasPermission="system:${classInfo.className?uncap_first}:remove">
                <i class="fa fa-remove"></i> 删除
            </a>
        </div>
        <div class="col-sm-12 select-table table-striped">
            <table id="bootstrap-table" data-mobile-responsive="true"></table>
        </div>
    </div>
</div>
<th:block th:include="include :: footer"/>
<#if createTimeFlag == 1>
    <th:block th:include="include :: datetimepicker-js" />
</#if>
<script th:inline="javascript">
    var editFlag = [[$...{@permission.hasPermi('system:${classInfo.className?uncap_first}:edit')}]];
    var removeFlag = [[$...{@permission.hasPermi('system:${classInfo.className?uncap_first}:remove')}]];
    var prefix = ctx + "admin/system/${classInfo.className?uncap_first}";

    $(function () {
        var options = {
            url: prefix + "/list",
            createUrl: prefix + "/add",
            updateUrl: prefix + "/edit/{id}",
            removeUrl: prefix + "/remove",
            modalName: "${classInfo.className?uncap_first}",
            columns: [{
                checkbox: true
            },
            <#list classInfo.fieldList as fieldItem>
            <#if fieldItem.fieldName=='status'>
                {
                    visible: editFlag == 'hidden' ? false : true,
                    title: '状态',
                    align: 'center',
                    formatter: function (value, row, index) {
                        return statusTools(row);
                    }
                },
            <#elseif fieldItem.fieldName==classInfo.conversionPrimaryKey>
            <#elseif fieldItem.fieldName=='createTime'>
                {
                    field: 'createTime',
                    title: '创建时间',
                    sortable: true,
                    formatter:function (value,item,index) {
                        return $.common.formatTimestamp(value);
                    }
                },
            <#else>
                {
                    field: '${fieldItem.fieldName}',
                    title: '${fieldItem.fieldComment}'
                },
            </#if>
            </#list>
            {
                title: '操作',
                align: 'center',
                formatter: function (value, row, index) {
                    var actions = [];
                    actions.push('<a class="btn btn-success btn-xs ' + editFlag + '" href="javascript:void(0)" onclick="$.operate.edit(\'' + row.${classInfo.primaryKey} + '\')"><i class="fa fa-edit"></i>编辑</a> ');
                    actions.push('<a class="btn btn-danger btn-xs ' + removeFlag + '" href="javascript:void(0)" onclick="$.operate.remove(\'' + row.${classInfo.primaryKey} + '\')"><i class="fa fa-remove"></i>删除</a>');
                    return actions.join('');
                }
            }]
        };
        $.table.init(options);
    });
</script>
</body>
</html>