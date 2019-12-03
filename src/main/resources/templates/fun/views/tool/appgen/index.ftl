<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8"/>
    <title>FUN-BOOT代码生成</title>
    <#import "common/common.macro.ftl" as netCommon>
    <@netCommon.commonStyle />
    <link rel="stylesheet" href="${request.contextPath}/gen/plugins/codemirror/lib/codemirror.css">
    <link rel="stylesheet" href="${request.contextPath}/gen/plugins/codemirror/addon/hint/show-hint.css">

</head>
<body class="hold-transition skin-blue layout-top-nav ">
<div class="wrapper">

    <#-- header -->
    <@netCommon.commonHeader />

    <#-- content -->
    <div class="content-wrapper">
        <div class="container">

            <section class="content">

                <div class="row">

                    <#-- left -->
                    <div class2="col-md-9">

                        <#-- 表结构 -->
                        <div class="box box-default">
                            <div class="box-header with-border">
                                <h4 class="pull-left">表结构（DDL）信息</h4>
                                <button type="button" class="btn btn-default btn-xs pull-right" id="codeGenerate">生成代码
                                </button>
                            </div>
                            <div class="box-body">
                                <ul class="chart-legend clearfix">
                                    <li>
                                        <small class="text-muted">
                                            <textarea id="tableSql" placeholder="请输入表结构信息...">
CREATE TABLE `ums_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL COMMENT '用户名',
  `create_time` bigint(15) DEFAULT NULL,
  `status` char(1) DEFAULT '1' COMMENT '0-禁用1-正常',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='用户信息';
                                            </textarea>
                                        </small>
                                    </li>
                                </ul>
                            </div>
                        </div>


                        <#-- 代码显示区域 -->
                        <div class="nav-tabs-custom">
                            <!-- Tabs within a box -->
                            <ul class="nav nav-tabs pull-right">
                                <li class="pull-left header">生成代码</li>
                                <li><a href="#model"        data-toggle="tab">Entity</a></li>
                                <li><a href="#mybatis"      data-toggle="tab">Mybatis</a></li>
                                <li><a href="#dao"          data-toggle="tab">Mapper</a></li>
                                <li><a href="#service_impl" data-toggle="tab">ServiceImpl</a></li>
                                <li><a href="#service"      data-toggle="tab">Service</a></li>
                                <li class="active"><a href="#controller" data-toggle="tab">Controller</a></li>
                            </ul>
                            <div class="tab-content no-padding">
                                <div class="chart tab-pane active" id="controller">
                                    <div class="box-body">
                                        controller.java：<textarea id="controller_ide"></textarea>
                                    </div>
                                </div>
                                <div class="chart tab-pane active" id="service">
                                    <div class="box-body">
                                        service.java：<textarea id="service_ide"></textarea>
                                    </div>
                                </div>
                                <div class="chart tab-pane active" id="service_impl">
                                    <div class="box-body">
                                        serviceImpl.java：<textarea id="service_impl_ide"></textarea>
                                    </div>
                                </div>
                                <div class="chart tab-pane active" id="dao">
                                    <div class="box-body">
                                        mapper.java：<textarea id="dao_ide"></textarea>
                                    </div>
                                </div>
                                <div class="chart tab-pane active" id="mybatis">
                                    <div class="box-body">
                                        mapper.xml：<textarea id="mybatis_ide"></textarea>
                                    </div>
                                </div>
                                <div class="chart tab-pane active" id="model">
                                    <div class="box-body ">
                                        entity.java：<textarea id="model_ide"></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </div>

    <!-- footer -->
    <@netCommon.commonFooter />
</div>

<@netCommon.commonScript />
<script src="${request.contextPath}/gen/plugins/codemirror/lib/codemirror.js"></script>
<script src="${request.contextPath}/gen/plugins/codemirror/addon/hint/show-hint.js"></script>
<script src="${request.contextPath}/gen/plugins/codemirror/addon/hint/anyword-hint.js"></script>
<script src="${request.contextPath}/gen/plugins/codemirror/addon/display/placeholder.js"></script>
<script src="${request.contextPath}/gen/plugins/codemirror/mode/clike/clike.js"></script>
<script src="${request.contextPath}/gen/plugins/codemirror/mode/sql/sql.js"></script>
<script src="${request.contextPath}/gen/plugins/codemirror/mode/xml/xml.js"></script>
<script src="${request.contextPath}/gen/js/index.js"></script>
</body>
</html>
