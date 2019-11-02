/*
MySQL 5.7.24
Database: fun_base
Backup Time: 2019-11-01 22:49:14
*/

SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `fun_base`.`sys_config`;
DROP TABLE IF EXISTS `fun_base`.`sys_dict_data`;
DROP TABLE IF EXISTS `fun_base`.`sys_dict_type`;
DROP TABLE IF EXISTS `fun_base`.`sys_login_log`;
DROP TABLE IF EXISTS `fun_base`.`sys_menu`;
DROP TABLE IF EXISTS `fun_base`.`sys_notice`;
DROP TABLE IF EXISTS `fun_base`.`sys_oper_log`;
DROP TABLE IF EXISTS `fun_base`.`sys_role`;
DROP TABLE IF EXISTS `fun_base`.`sys_role_menu`;
DROP TABLE IF EXISTS `fun_base`.`sys_user`;
DROP TABLE IF EXISTS `fun_base`.`sys_user_role`;
DROP TABLE IF EXISTS `fun_base`.`ums_user`;
CREATE TABLE `sys_config` (
  `config_id` int(5) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` bigint(15) DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` bigint(15) DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='参数配置表';
CREATE TABLE `sys_dict_data` (
  `dict_code` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int(4) DEFAULT '0' COMMENT '字典排序',
  `dict_label` varchar(100) DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` bigint(15) DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` bigint(15) DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='字典数据表';
CREATE TABLE `sys_dict_type` (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` bigint(15) DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` bigint(15) DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE KEY `dict_type` (`dict_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='字典类型表';
CREATE TABLE `sys_login_log` (
  `info_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(25) DEFAULT NULL COMMENT '登录账号',
  `login_location` varchar(100) DEFAULT NULL COMMENT '登录地点',
  `ipaddr` varchar(50) DEFAULT NULL COMMENT 'ip地址',
  `os` varchar(50) DEFAULT NULL COMMENT '操作系统',
  `browser` varchar(50) DEFAULT NULL COMMENT '浏览器',
  `msg` varchar(255) DEFAULT NULL,
  `create_time` bigint(15) DEFAULT NULL COMMENT '访问时间',
  `status` char(1) DEFAULT '0' COMMENT '0-成功1-失败',
  PRIMARY KEY (`info_id`)
) ENGINE=InnoDB AUTO_INCREMENT=182 DEFAULT CHARSET=utf8mb4;
CREATE TABLE `sys_menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(40) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `order_num` int(4) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL COMMENT '请求地址',
  `target` varchar(20) CHARACTER SET utf8 DEFAULT '' COMMENT '打开方式（menuItem页签 menuBlank新窗口）',
  `menu_type` char(1) CHARACTER SET utf8 DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8 DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `perms` varchar(100) CHARACTER SET utf8 DEFAULT '' COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8 DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(30) CHARACTER SET utf8 DEFAULT '' COMMENT '创建者',
  `create_time` bigint(15) DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(30) CHARACTER SET utf8 DEFAULT '' COMMENT '更新者',
  `update_time` bigint(15) DEFAULT NULL COMMENT '更新时间',
  `remark` text CHARACTER SET utf8 COMMENT '备注',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;
CREATE TABLE `sys_notice` (
  `notice_id` int(4) NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) NOT NULL COMMENT '公告标题',
  `notice_type` char(1) NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` varchar(2000) DEFAULT NULL COMMENT '公告内容',
  `status` char(1) DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` bigint(15) DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` bigint(15) DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='通知公告表';
CREATE TABLE `sys_oper_log` (
  `oper_id` int(11) NOT NULL AUTO_INCREMENT,
  `oper_name` varchar(40) DEFAULT NULL COMMENT '操作名称',
  `oper_ip` varchar(20) DEFAULT NULL COMMENT '操作者IP',
  `oper_location` varchar(100) DEFAULT NULL COMMENT '操作者地点',
  `error_msg` text COMMENT '错误信息',
  `login_name` varchar(30) DEFAULT NULL COMMENT '登录账号',
  `time` bigint(13) DEFAULT NULL COMMENT '操作耗时（ms）',
  `method` varchar(200) DEFAULT NULL COMMENT '操作方法',
  `oper_param` varchar(300) DEFAULT NULL COMMENT '方法参数',
  `create_time` bigint(13) DEFAULT NULL,
  `status` char(1) NOT NULL DEFAULT '1' COMMENT '0-错误1-正常',
  PRIMARY KEY (`oper_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;
CREATE TABLE `sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(30) DEFAULT NULL,
  `role_key` varchar(100) DEFAULT NULL COMMENT '角色权限字符串',
  `role_sort` int(4) DEFAULT NULL COMMENT '显示顺序',
  `status` char(1) DEFAULT '1' COMMENT '0-禁用1-正常',
  `del_flag` char(1) DEFAULT '1' COMMENT '2-已删除1-正常',
  `create_by` varchar(30) DEFAULT NULL,
  `create_time` bigint(15) DEFAULT NULL,
  `update_by` varchar(30) DEFAULT NULL,
  `update_time` bigint(15) DEFAULT NULL,
  `remark` text,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
CREATE TABLE `sys_role_menu` (
  `role_id` int(11) DEFAULT NULL,
  `menu_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE `sys_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(30) DEFAULT NULL,
  `username` varchar(30) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `telephone` varchar(11) DEFAULT NULL,
  `sex` char(1) DEFAULT '2' COMMENT '0-男1-女2-未知',
  `avatar` varchar(120) DEFAULT NULL COMMENT '头像路径',
  `password` varchar(50) CHARACTER SET utf8 DEFAULT '' COMMENT '密码',
  `salt` varchar(20) CHARACTER SET utf8 DEFAULT '' COMMENT '盐加密',
  `status` char(1) CHARACTER SET utf8 DEFAULT '1' COMMENT '帐号状态（0禁用 1正常）',
  `del_flag` char(1) CHARACTER SET utf8 DEFAULT '1' COMMENT '删除标志（1代表存在 2代表删除）',
  `login_ip` varchar(50) CHARACTER SET utf8 DEFAULT '' COMMENT '最后登陆IP',
  `login_date` bigint(15) DEFAULT NULL COMMENT '最后登陆时间',
  `create_by` varchar(25) CHARACTER SET utf8 DEFAULT '' COMMENT '创建者',
  `create_time` bigint(15) DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(25) CHARACTER SET utf8 DEFAULT '' COMMENT '更新者',
  `update_time` bigint(15) DEFAULT NULL COMMENT '更新时间',
  `remark` text CHARACTER SET utf8 COMMENT '备注',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
CREATE TABLE `sys_user_role` (
  `user_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE `ums_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(25) DEFAULT NULL COMMENT '登录账号',
  `username` varchar(20) DEFAULT NULL COMMENT '用户名',
  `u_account` varchar(25) DEFAULT NULL COMMENT 'u号',
  `user_type` varchar(2) DEFAULT NULL COMMENT '用户类型（00系统用户）',
  `user_level` int(2) DEFAULT NULL COMMENT '用户等级',
  `email` varchar(30) DEFAULT NULL,
  `sex` char(1) CHARACTER SET utf8 DEFAULT '2' COMMENT '用户性别（0男 1女 2未知）',
  `password` varchar(25) DEFAULT NULL,
  `salt` varchar(20) DEFAULT NULL COMMENT '加密盐',
  `avatar` varchar(100) DEFAULT NULL COMMENT '头像路径',
  `telephone` varchar(15) DEFAULT NULL,
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（1代表存在 2代表删除）',
  `login_ip` varchar(30) CHARACTER SET utf8 DEFAULT '' COMMENT '最后登陆IP',
  `login_date` bigint(15) DEFAULT NULL COMMENT '最后登陆时间',
  `open_id` varchar(30) DEFAULT NULL COMMENT '开放id',
  `is_lock` char(1) DEFAULT '0' COMMENT '0-可修改u号1-不可修改',
  `remark` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '备注',
  `honor` int(3) DEFAULT '400' COMMENT '信誉度',
  `exp` int(10) DEFAULT '0' COMMENT '经验',
  `fans_num` int(11) DEFAULT '0' COMMENT '粉丝量',
  `follow_num` int(11) DEFAULT '0' COMMENT '关注量',
  `oid` char(1) DEFAULT '4' COMMENT '个人信息公开程度:0-自己可见，1-同班，2-同系，3-同校，4-全部',
  `health` int(2) DEFAULT '100' COMMENT '健康度',
  `ban_time` bigint(15) DEFAULT NULL COMMENT '禁用账号到期时间',
  `update_time` bigint(15) DEFAULT NULL,
  `create_time` bigint(15) DEFAULT NULL,
  `is_verify` char(1) DEFAULT '0' COMMENT '1-已认证，0-未认证',
  `status` char(1) DEFAULT '1' COMMENT '0-禁用1-正常',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
BEGIN;
LOCK TABLES `fun_base`.`sys_config` WRITE;
DELETE FROM `fun_base`.`sys_config`;
INSERT INTO `fun_base`.`sys_config` (`config_id`,`config_name`,`config_key`,`config_value`,`config_type`,`create_by`,`create_time`,`update_by`,`update_time`,`remark`) VALUES (1, '主框架页-默认皮肤样式名称', 'sys.index.skinName', 'skin-blue', 'Y', 'admin', 20180316113300, 'ry', 20180316113300, '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow'),(2, '用户管理-账号初始密码', 'sys.user.initPassword', '123456', 'Y', 'admin', 20180316113300, 'ry', 20180316113300, '初始化密码 123456'),(3, '主框架页-侧边栏主题', 'sys.index.sideTheme', 'theme-dark', 'Y', 'admin', 20180316113300, 'ry', 20180316113300, '深色主题theme-dark，浅色主题theme-light');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `fun_base`.`sys_dict_data` WRITE;
DELETE FROM `fun_base`.`sys_dict_data`;
INSERT INTO `fun_base`.`sys_dict_data` (`dict_code`,`dict_sort`,`dict_label`,`dict_value`,`dict_type`,`css_class`,`list_class`,`is_default`,`status`,`create_by`,`create_time`,`update_by`,`update_time`,`remark`) VALUES (1, 1, '男', '0', 'sys_user_sex', '', '', 'Y', '0', 'admin', 20180316113300, 'ry', 20180316113300, '性别男'),(2, 2, '女', '1', 'sys_user_sex', '', '', 'N', '0', 'admin', 20180316113300, 'ry', 20180316113300, '性别女'),(3, 3, '未知', '2', 'sys_user_sex', '', '', 'N', '0', 'admin', 20180316113300, 'ry', 20180316113300, '性别未知'),(4, 1, '显示', '0', 'sys_show_hide', '', 'primary', 'Y', '0', 'admin', 20180316113300, 'ry', 20180316113300, '显示菜单'),(5, 2, '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', '0', 'admin', 20180316113300, 'ry', 20180316113300, '隐藏菜单'),(6, 1, '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', '0', 'admin', 20180316113300, 'ry', 20180316113300, '正常状态'),(7, 2, '停用', '1', 'sys_normal_disable', '', 'danger', 'N', '0', 'admin', 20180316113300, 'ry', 20180316113300, '停用状态'),(8, 1, '正常', '0', 'sys_job_status', '', 'primary', 'Y', '0', 'admin', 20180316113300, 'ry', 20180316113300, '正常状态'),(9, 2, '暂停', '1', 'sys_job_status', '', 'danger', 'N', '0', 'admin', 20180316113300, 'ry', 20180316113300, '停用状态'),(10, 1, '默认', 'DEFAULT', 'sys_job_group', '', '', 'Y', '0', 'admin', 20180316113300, 'ry', 20180316113300, '默认分组'),(11, 2, '系统', 'SYSTEM', 'sys_job_group', '', '', 'N', '0', 'admin', 20180316113300, 'ry', 20180316113300, '系统分组'),(12, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', '0', 'admin', 20180316113300, 'ry', 20180316113300, '系统默认是'),(13, 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', '0', 'admin', 20180316113300, 'ry', 20180316113300, '系统默认否'),(14, 1, '通知', '1', 'sys_notice_type', '', 'warning', 'Y', '0', 'admin', 20180316113300, 'ry', 20180316113300, '通知'),(15, 2, '公告', '2', 'sys_notice_type', '', 'success', 'N', '0', 'admin', 20180316113300, 'ry', 20180316113300, '公告'),(16, 1, '正常', '0', 'sys_notice_status', '', 'primary', 'Y', '0', 'admin', 20180316113300, 'ry', 20180316113300, '正常状态'),(17, 2, '关闭', '1', 'sys_notice_status', '', 'danger', 'N', '0', 'admin', 20180316113300, 'ry', 20180316113300, '关闭状态'),(18, 1, '新增', '1', 'sys_oper_type', '', 'info', 'N', '0', 'admin', 20180316113300, 'ry', 20180316113300, '新增操作'),(19, 2, '修改', '2', 'sys_oper_type', '', 'info', 'N', '0', 'admin', 20180316113300, 'ry', 20180316113300, '修改操作'),(20, 3, '删除', '3', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', 20180316113300, 'ry', 20180316113300, '删除操作'),(21, 4, '授权', '4', 'sys_oper_type', '', 'primary', 'N', '0', 'admin', 20180316113300, 'ry', 20180316113300, '授权操作'),(22, 5, '导出', '5', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', 20180316113300, 'ry', 20180316113300, '导出操作'),(23, 6, '导入', '6', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', 20180316113300, 'ry', 20180316113300, '导入操作'),(24, 7, '强退', '7', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', 20180316113300, 'ry', 20180316113300, '强退操作'),(25, 8, '生成代码', '8', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', 20180316113300, 'ry', 20180316113300, '生成操作'),(26, 9, '清空数据', '9', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', 20180316113300, 'ry', 20180316113300, '清空操作'),(27, 1, '成功', '0', 'sys_common_status', '', 'primary', 'N', '0', 'admin', 20180316113300, 'ry', 20180316113300, '正常状态'),(28, 2, '失败', '1', 'sys_common_status', '', 'danger', 'N', '0', 'admin', 20180316113300, 'ry', 20180316113300, '停用状态'),(30, 1, '早餐', '1', 'sys_meal_type', '', '', 'N', '0', 'admin', 20190820151654, 'admin', 20190820151846, ''),(31, 2, '午餐', '2', 'sys_meal_type', '', '', 'N', '0', 'admin', 20190820151715, 'admin', 20190821102117, ''),(32, 3, '晚餐', '3', 'sys_meal_type', '', '', 'N', '0', 'admin', 20190820151912, 'admin', 20190822130712, '');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `fun_base`.`sys_dict_type` WRITE;
DELETE FROM `fun_base`.`sys_dict_type`;
INSERT INTO `fun_base`.`sys_dict_type` (`dict_id`,`dict_name`,`dict_type`,`status`,`create_by`,`create_time`,`update_by`,`update_time`,`remark`) VALUES (1, '用户性别', 'sys_user_sex', '0', 'admin', 20180316113300, 'admin', 20180316113300, '用户性别列表'),(2, '菜单状态', 'sys_show_hide', '0', 'admin', 20180316113300, 'admin', 20180316113300, '菜单状态列表'),(3, '系统开关', 'sys_normal_disable', '0', 'admin', 20180316113300, 'admin', 20180316113300, '系统开关列表'),(4, '任务状态', 'sys_job_status', '0', 'admin', 20180316113300, 'admin', 20180316113300, '任务状态列表'),(5, '任务分组', 'sys_job_group', '0', 'admin', 20180316113300, 'admin', 20180316113300, '任务分组列表'),(6, '系统是否', 'sys_yes_no', '0', 'admin', 20180316113300, 'admin', 20180316113300, '系统是否列表'),(7, '通知类型', 'sys_notice_type', '0', 'admin', 20180316113300, 'admin', 20180316113300, '通知类型列表'),(8, '通知状态', 'sys_notice_status', '0', 'admin', 20180316113300, 'admin', 20180316113300, '通知状态列表'),(9, '操作类型', 'sys_oper_type', '0', 'admin', 20180316113300, 'admin', 20180316113300, '操作类型列表'),(10, '系统状态', 'sys_common_status', '0', 'admin', 20180316113300, 'admin', 20180316113300, '登录状态列表'),(11, '餐类型', 'sys_meal_type', '0', 'admin', 20190820151515, 'admin', 20190820152000, '餐类型列表');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `fun_base`.`sys_login_log` WRITE;
DELETE FROM `fun_base`.`sys_login_log`;
INSERT INTO `fun_base`.`sys_login_log` (`info_id`,`login_name`,`login_location`,`ipaddr`,`os`,`browser`,`msg`,`create_time`,`status`) VALUES (49, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1568605653883, '0'),(50, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1568605656657, '0'),(51, 'admin', '内网IP', '127.0.0.1', 'Unknown', 'Unknown', '登录成功', 1568633759256, '0'),(52, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1568633836708, '0'),(53, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1568734483151, '0'),(54, 'admin', '内网IP', '127.0.0.1', 'Unknown', 'Unknown', '登录失败', 1568734696410, '1'),(55, 'admin', '内网IP', '127.0.0.1', 'Unknown', 'Unknown', '登录失败', 1568734767290, '1'),(56, 'admin', '内网IP', '127.0.0.1', 'Unknown', 'Unknown', '登录失败', 1568734775653, '1'),(57, 'admin', '内网IP', '127.0.0.1', 'Unknown', 'Unknown', '登录成功', 1568735213558, '0'),(58, 'admin', '内网IP', '127.0.0.1', 'Unknown', 'Unknown', '登录成功', 1569251315678, '0'),(59, 'admin', '内网IP', '127.0.0.1', 'Unknown', 'Unknown', '登录成功', 1569252012500, '0'),(60, 'admin', '内网IP', '127.0.0.1', 'Unknown', 'Unknown', '登录成功', 1569254673425, '0'),(61, 'admin', '内网IP', '127.0.0.1', 'Unknown', 'Unknown', '登录成功', 1569254724325, '0'),(62, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1569767866878, '0'),(63, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1569769168705, '0'),(64, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Firefox', '登录成功', 1569769855153, '0'),(65, 'admin', 'XX XX', '171.211.68.123', 'Windows 10', 'Chrome', '用户不存在/密码错误', 1569770741424, '1'),(66, 'admin', '内网IP', '10.127.106.2', 'Windows 10', 'Chrome', '登录成功', 1569815606643, '0'),(67, 'admin', '内网IP', '10.127.105.201', 'Windows 10', 'Chrome', '登录成功', 1569825672348, '0'),(68, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1569833049083, '0'),(69, 'mrdjun', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '用户不存在/密码错误', 1569833656240, '1'),(70, 'mrdjun', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '用户不存在/密码错误', 1569833941235, '1'),(71, 'mrdjun', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1569836327884, '0'),(72, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '验证码错误', 1569912440442, '1'),(73, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '验证码错误', 1569912448670, '1'),(74, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '验证码错误', 1569917169276, '1'),(75, 'mrdjun', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '用户不存在/密码错误', 1569918961305, '1'),(76, 'mrdjun', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '用户不存在/密码错误', 1569918973844, '1'),(77, 'mrdjun', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '验证码错误', 1569918984648, '1'),(78, 'mrdjun', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '验证码错误', 1569919097356, '1'),(79, 'mrdjun', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '用户不存在/密码错误', 1569919109290, '1'),(80, 'mrdjun', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1569919805938, '0'),(81, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1569922372302, '0'),(82, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Firefox', '登录成功', 1569978302313, '0'),(83, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1569986107952, '0'),(84, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1570004703451, '0'),(85, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '验证码错误', 1570005159351, '1'),(86, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1570005164209, '0'),(87, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1570015133444, '0'),(88, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Firefox', '登录成功', 1570028929798, '0'),(89, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1570031314505, '0'),(90, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Firefox', '登录成功', 1570031349952, '0'),(91, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Firefox', '登录成功', 1570031706871, '0'),(92, 'admin', '内网IP', '10.127.105.201', 'Windows 10', 'Chrome', '登录成功', 1570538129261, '0'),(93, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1570599997774, '0'),(94, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1570600288745, '0'),(95, 'admin', '内网IP', '192.168.86.1', 'Windows 10', 'Chrome', '登录成功', 1570608178633, '0'),(96, 'admin', '内网IP', '10.127.106.2', 'Windows 10', 'Chrome', '登录成功', 1570859951942, '0'),(97, 'admin', '内网IP', '10.127.106.2', 'Windows 10', 'Chrome', '登录成功', 1571273723005, '0'),(98, 'admin', '内网IP', '10.127.106.2', 'Windows 10', 'Firefox', '登录成功', 1571274417513, '0'),(99, 'admin', 'XX XX', '118.120.234.58', 'Windows 10', 'Chrome', '登录成功', 1571291111557, '0'),(100, 'admin', 'XX XX', '171.211.33.42', 'Windows 10', 'Chrome', '登录成功', 1571494054409, '0'),(101, 'admin', 'XX XX', '118.120.187.51', 'Windows 10', 'Chrome', '登录成功', 1571579999642, '0'),(102, 'admin', 'XX XX', '118.120.187.51', 'Windows 10', 'Chrome', '登录成功', 1571581529079, '0'),(103, 'admin', '内网IP', '192.168.86.1', 'Windows 10', 'Chrome', '验证码错误', 1571583622338, '1'),(104, 'admin', '内网IP', '192.168.86.1', 'Windows 10', 'Chrome', '验证码错误', 1571583631823, '1'),(105, 'admin', '内网IP', '192.168.86.1', 'Windows 10', 'Chrome', '验证码错误', 1571583716307, '1'),(106, 'admin', '内网IP', '192.168.86.1', 'Windows 10', 'Chrome', '登录成功', 1571583946258, '0'),(107, 'admin', '内网IP', '192.168.86.1', 'Windows 10', 'Chrome', '登录成功', 1571584276839, '0'),(108, 'admin', '内网IP', '192.168.86.1', 'Windows 10', 'Chrome', '验证码错误', 1571584301854, '1'),(109, 'admin', '内网IP', '192.168.86.1', 'Windows 10', 'Chrome', '验证码错误', 1571584721105, '1'),(110, 'admin', '内网IP', '192.168.86.1', 'Windows 10', 'Chrome', '登录成功', 1571584963970, '0'),(111, 'admin', '内网IP', '10.127.106.2', 'Windows 10', 'Chrome', '登录成功', 1571618109509, '0'),(112, 'admin', '内网IP', '10.127.105.201', 'Windows 10', 'Chrome', '登录成功', 1571642159102, '0'),(113, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1571650384942, '0'),(114, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1571652128864, '0'),(115, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1571657609536, '0'),(116, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1571708212314, '0'),(117, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1571710315457, '0'),(118, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Firefox', '登录成功', 1571710422859, '0'),(119, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1571718839982, '0'),(120, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1571733147488, '0'),(121, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1571736335477, '0'),(122, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1571740334211, '0'),(123, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1571745466217, '0'),(124, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1571753723687, '0'),(125, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1571755070926, '0'),(126, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1571756502285, '0'),(127, 'admin', '内网IP', '10.127.106.2', 'Windows 10', 'Chrome', '登录成功', 1571877230191, '0'),(128, 'admin', '内网IP', '10.127.106.2', 'Windows 10', 'Firefox', '登录成功', 1571878039321, '0'),(129, 'admin', '内网IP', '10.127.106.2', 'Windows 10', 'Firefox', '登录成功', 1571882047359, '0'),(130, 'admin', 'XX XX', '118.120.228.243', 'Windows 10', 'Chrome', '登录成功', 1572004192945, '0'),(131, 'admin', 'XX XX', '118.120.228.243', 'Windows 10', 'Firefox', '验证码错误', 1572004362175, '1'),(132, 'admin', 'XX XX', '118.120.228.243', 'Windows 10', 'Firefox', '登录成功', 1572004402828, '0'),(133, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Internet Explorer 11', '登录成功', 1572004549506, '0'),(134, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1572004716771, '0'),(135, 'admin', 'XX XX', '171.211.102.160', 'Windows 10', 'Chrome', '登录成功', 1572004902434, '0'),(136, 'admin', 'XX XX', '171.211.102.160', 'Windows 10', 'Firefox', '登录成功', 1572005655676, '0'),(137, 'admin', 'XX XX', '171.211.102.160', 'Windows 10', 'Firefox', '登录成功', 1572006877136, '0'),(138, 'admin', 'XX XX', '117.136.82.199', 'Android Mobile', 'Chrome Mobile', '验证码错误', 1572006999700, '1'),(139, 'admin', 'XX XX', '117.136.82.199', 'Android Mobile', 'Chrome Mobile', '登录成功', 1572007020020, '0'),(140, 'admin', 'XX XX', '171.211.102.160', 'Windows 10', 'Chrome', '登录成功', 1572007819502, '0'),(141, 'admin', 'XX XX', '171.211.102.160', 'Windows 10', 'Chrome', '登录成功', 1572007891412, '0'),(142, 'admin', '四川 德阳', '171.211.102.160', 'Windows 10', 'Chrome', '登录成功', 1572007928786, '0'),(143, 'admin', 'XX XX', '171.211.32.181', 'Windows 10', 'Chrome', '登录成功', 1572336513125, '0'),(144, 'admin', '四川 德阳', '171.211.32.181', 'Windows 10', 'Chrome', '验证码错误', 1572337763233, '1'),(145, 'admin', '四川 德阳', '171.211.32.181', 'Windows 10', 'Chrome', '验证码错误', 1572338495682, '1'),(146, 'admin', '四川 德阳', '171.211.32.181', 'Windows 10', 'Chrome', '验证码错误', 1572338507622, '1'),(147, 'admin', '四川 德阳', '171.211.32.181', 'Windows 10', 'Chrome', '验证码错误', 1572338600140, '1'),(148, 'admin', '四川 德阳', '171.211.32.181', 'Windows 10', 'Chrome', '验证码错误', 1572338686634, '1'),(149, 'admin', '四川 德阳', '171.211.32.181', 'Windows 10', 'Chrome', '验证码错误', 1572338870928, '1'),(150, 'admin', '四川 德阳', '171.211.32.181', 'Windows 10', 'Chrome', '验证码错误', 1572338930664, '1'),(151, 'admin', '四川 德阳', '171.211.32.181', 'Windows 10', 'Chrome', '验证码错误', 1572339532456, '1'),(152, 'admin', '四川 德阳', '171.211.32.181', 'Windows 10', 'Chrome', '验证码错误', 1572339600384, '1'),(153, 'admin', '四川 德阳', '171.211.32.181', 'Windows 10', 'Chrome', '验证码错误', 1572339717040, '1'),(154, 'admin', '四川 德阳', '171.211.32.181', 'Windows 10', 'Chrome', '验证码错误', 1572339907144, '1'),(155, 'admin', '四川 德阳', '171.211.32.181', 'Windows 10', 'Chrome', '验证码错误', 1572340423855, '1'),(156, 'admin', '四川 德阳', '171.211.32.181', 'Windows 10', 'Chrome', '验证码错误', 1572340428647, '1'),(157, 'admin', '四川 德阳', '171.211.32.181', 'Windows 10', 'Chrome', '验证码错误', 1572340439986, '1'),(158, 'admin', '四川 德阳', '171.211.32.181', 'Windows 10', 'Chrome', '验证码错误', 1572340441255, '1'),(159, 'admin', '四川 德阳', '171.211.32.181', 'Windows 10', 'Chrome', '验证码错误', 1572340441850, '1'),(160, 'admin', '四川 德阳', '171.211.32.181', 'Windows 10', 'Chrome', '验证码错误', 1572340603944, '1'),(161, 'admin', '四川 德阳', '171.211.32.181', 'Windows 10', 'Chrome', '验证码错误', 1572340679378, '1'),(162, 'admin', '四川 德阳', '171.211.32.181', 'Windows 10', 'Chrome', '验证码错误', 1572340742994, '1'),(163, 'admin', '四川 德阳', '171.211.32.181', 'Windows 10', 'Chrome', '验证码错误', 1572341027421, '1'),(164, 'admin', '四川 德阳', '171.211.32.181', 'Windows 10', 'Chrome', '验证码错误', 1572341075153, '1'),(165, 'admin', 'XX XX', '171.211.32.181', 'Windows 10', 'Chrome', '登录成功', 1572346496320, '0'),(166, 'admin', 'XX XX', '171.211.32.181', 'Windows 10', 'Chrome', '登录成功', 1572346551872, '0'),(167, 'admin', 'XX XX', '171.211.32.181', 'Windows 10', 'Chrome', '登录成功', 1572346726246, '0'),(168, 'admin', 'XX XX', '171.211.32.181', 'Windows 10', 'Chrome', '登录成功', 1572346883023, '0'),(169, 'admin', '四川 德阳', '171.211.32.181', 'Windows 10', 'Firefox', '登录成功', 1572348923282, '0'),(170, 'admin', '四川 德阳', '171.211.32.181', 'Windows 10', 'Firefox', '登录成功', 1572351118266, '0'),(171, 'admin', '四川 德阳', '171.211.32.181', 'Windows 10', 'Firefox', '登录成功', 1572356099223, '0'),(172, 'admin', '四川 德阳', '171.211.32.181', 'Windows 10', 'Chrome', '登录成功', 1572359619559, '0'),(173, 'admin', '四川 德阳', '171.211.32.181', 'Windows 10', 'Chrome', '登录成功', 1572360109347, '0'),(174, 'admin', '内网IP', '192.168.86.1', 'Windows 10', 'Chrome', '验证码错误', 1572361144421, '1'),(175, 'admin', '内网IP', '192.168.86.1', 'Windows 10', 'Chrome', '登录成功', 1572361147041, '0'),(176, 'admin', '内网IP', '192.168.86.1', 'Windows 10', 'Firefox 7', '登录成功', 1572361615249, '0'),(177, 'admin', '内网IP', '192.168.86.1', 'Windows 10', 'Chrome', '登录成功', 1572397960299, '0'),(178, 'admin', '内网IP', '192.168.86.1', 'Windows 10', 'Chrome', '验证码错误', 1572398545682, '1'),(179, 'admin', '内网IP', '192.168.86.1', 'Windows 10', 'Chrome', '验证码错误', 1572398551048, '1'),(180, 'admin', '内网IP', '192.168.86.1', 'Windows 10', 'Chrome', '验证码错误', 1572411747106, '1'),(181, 'admin', '内网IP', '192.168.86.1', 'Windows 10', 'Chrome', '登录成功', 1572411750073, '0');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `fun_base`.`sys_menu` WRITE;
DELETE FROM `fun_base`.`sys_menu`;
INSERT INTO `fun_base`.`sys_menu` (`menu_id`,`menu_name`,`parent_id`,`order_num`,`url`,`target`,`menu_type`,`visible`,`perms`,`icon`,`create_by`,`create_time`,`update_by`,`update_time`,`remark`) VALUES (1, '系统管理', 0, 1, '#', '', 'M', '0', '', 'fa fa-gear', 'admin', 1571878039321, 'admin', 1571878039321, '系统管理目录'),(2, '系统监控', 0, 2, '#', '', 'M', '0', '', 'fa fa-heartbeat', 'admin', 1571878039321, 'admin', 1571878039321, '系统监控目录'),(3, '系统工具', 0, 3, '#', '', 'M', '0', '', 'fa fa-wrench', 'admin', 1571878039321, 'admin', 1571878039321, '系统工具目录'),(4, '用户管理', 1, 1, '/admin/user', 'menuItem', 'C', '0', 'system:user:view', '#', 'admin', 1571878039321, 'admin', 1571878039321, NULL),(5, '角色管理', 1, 2, '/admin/system/role', 'menuItem', 'C', '0', 'system:role:view', '#', 'admin', 1571878039321, 'admin', 1571878039321, NULL),(6, '菜单管理', 1, 3, '/admin/system/menu', 'menuItem', 'C', '0', 'system:menu:view', '#', 'admin', 1571878039321, 'admin', 1571878039321, NULL),(7, '代码生成工具', 3, 1, '/tool/gen', 'menuItem', 'C', '0', '', '#', 'admin', 1571878039321, 'admin', 1571878039321, NULL),(8, '字典管理', 1, 6, '/admin/system/dict', 'menuItem', 'C', '0', 'system:dict:view', '#', 'admin', 1571878039321, 'admin', 1571878039321, NULL);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `fun_base`.`sys_notice` WRITE;
DELETE FROM `fun_base`.`sys_notice`;
INSERT INTO `fun_base`.`sys_notice` (`notice_id`,`notice_title`,`notice_type`,`notice_content`,`status`,`create_by`,`create_time`,`update_by`,`update_time`,`remark`) VALUES (1, '温馨提醒：2019-07-01 项目启动', '2', '项目启动', '0', 'admin', NULL, 'admin', NULL, '管理员'),(2, '维护通知：2019-11-11 系统凌晨维护', '1', '维护内容', '0', 'admin', NULL, 'admin', NULL, '管理员');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `fun_base`.`sys_oper_log` WRITE;
DELETE FROM `fun_base`.`sys_oper_log`;
INSERT INTO `fun_base`.`sys_oper_log` (`oper_id`,`oper_name`,`oper_ip`,`oper_location`,`error_msg`,`login_name`,`time`,`method`,`oper_param`,`create_time`,`status`) VALUES (1, '清空操作日志', '127.0.0.1', '内网IP', NULL, 'admin', 10, 'com.fun.project.admin.monitor.log.controller.OperlogController.clean()', '{}', 1569251546501, '1'),(2, '获取登录日志列表', '127.0.0.1', '内网IP', NULL, 'admin', 13, 'com.fun.project.admin.monitor.log.controller.LoginLogController.loginLogList()', '{}', 1569251598658, '1'),(3, '获取登录日志列表', '127.0.0.1', '内网IP', NULL, 'admin', 10, 'com.fun.project.admin.monitor.log.controller.LoginLogController.loginLogList()', '{\"pageNum\":[\"2\"],\"pageSize\":[\"10\"]}', 1569251634044, '1'),(4, '获取登录日志列表', '127.0.0.1', '内网IP', NULL, 'admin', 11, 'com.fun.project.admin.monitor.log.controller.LoginLogController.loginLogList()', '{\"pageNum\":[\"2\"],\"pageSize\":[\"0\"]}', 1569251644408, '1'),(5, '执行 Redis memoryInfo 命令', '127.0.0.1', '内网IP', NULL, 'admin', 11, 'com.fun.project.admin.monitor.redis.RedisController.getMemoryInfo()', '{}', 1569251812881, '1'),(6, '获取登录日志列表', '127.0.0.1', '内网IP', NULL, 'admin', 15, 'com.fun.project.admin.monitor.controller.LoginLogController.loginLogList()', '{}', 1570031729552, '1'),(7, '获取操作日志列表', '127.0.0.1', '内网IP', NULL, 'admin', 17, 'com.fun.project.admin.monitor.controller.OperlogController.operList()', '{}', 1570032319389, '1'),(8, '通过Id查询DictType', '192.168.86.1', '内网IP', 'nested exception is org.apache.ibatis.reflection.ReflectionException: Could not set property \'dictId\' of \'class com.fun.project.admin.system.entity.dict.DictType\' with value \'1\' Cause: org.apache.ibatis.reflection.ReflectionException: There is no setter for property named \'dictId\' in \'class com.fun.project.admin.system.entity.dict.DictType\'', 'admin', 18, 'com.fun.project.admin.system.controller.DictTypeController.selectDictTypeById()', '{}', 1572417742204, '1'),(9, '通过Id查询DictType', '192.168.86.1', '内网IP', NULL, 'admin', 20, 'com.fun.project.admin.system.controller.DictTypeController.selectDictTypeById()', '{}', 1572417868428, '1'),(10, '通过Id查询DictData', '192.168.86.1', '内网IP', NULL, 'admin', 17, 'com.fun.project.admin.system.controller.DictDataController.selectDictDataById()', '{}', 1572418241955, '1'),(11, '通过Id查询DictData', '192.168.86.1', '内网IP', NULL, 'admin', 11, 'com.fun.project.admin.system.controller.DictDataController.selectDictDataById()', '{}', 1572418246110, '1'),(12, '通过Id查询DictData', '192.168.86.1', '内网IP', NULL, 'admin', 11, 'com.fun.project.admin.system.controller.DictDataController.selectDictDataById()', '{}', 1572418248657, '1');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `fun_base`.`sys_role` WRITE;
DELETE FROM `fun_base`.`sys_role`;
INSERT INTO `fun_base`.`sys_role` (`role_id`,`role_name`,`role_key`,`role_sort`,`status`,`del_flag`,`create_by`,`create_time`,`update_by`,`update_time`,`remark`) VALUES (1, '超级管理员', 'admin', 1, '1', '1', 'system', 1568439153, NULL, NULL, NULL),(2, '普通管理员', 'common', 2, '1', '1', 'system', 1568439153, NULL, NULL, NULL);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `fun_base`.`sys_role_menu` WRITE;
DELETE FROM `fun_base`.`sys_role_menu`;
INSERT INTO `fun_base`.`sys_role_menu` (`role_id`,`menu_id`) VALUES (1, 1),(1, 2);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `fun_base`.`sys_user` WRITE;
DELETE FROM `fun_base`.`sys_user`;
INSERT INTO `fun_base`.`sys_user` (`user_id`,`login_name`,`username`,`email`,`telephone`,`sex`,`avatar`,`password`,`salt`,`status`,`del_flag`,`login_ip`,`login_date`,`create_by`,`create_time`,`update_by`,`update_time`,`remark`) VALUES (1, 'admin', '超级管理员', 'mr.djun@qq.com', '18683827876', '0', '/profile/avatar/2019/09/06/11864680aedaf7e8dd08d52cdc816849.png', 'b35b89afc0e805106fdc6aeb0bc0f00f', '888888', '1', '1', '192.168.86.1', 1572411750086, 'system', 1570031706900, '', NULL, NULL),(2, 'mrdjun', '普通管理员', '903131009@qq.com', '15120016876', '0', '/profile/avatar/2019/09/06/11864680aedaf7e8dd08d52cdc816849.png', '164ee33c3f4182a185b4ccc0190f3dd6', '888888', '1', '1', '127.0.0.1', 1569919805952, 'system', 1570031706900, '', NULL, NULL);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `fun_base`.`sys_user_role` WRITE;
DELETE FROM `fun_base`.`sys_user_role`;
INSERT INTO `fun_base`.`sys_user_role` (`user_id`,`role_id`) VALUES (1, 1),(1, NULL);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `fun_base`.`ums_user` WRITE;
DELETE FROM `fun_base`.`ums_user`;
INSERT INTO `fun_base`.`ums_user` (`user_id`,`login_name`,`username`,`u_account`,`user_type`,`user_level`,`email`,`sex`,`password`,`salt`,`avatar`,`telephone`,`del_flag`,`login_ip`,`login_date`,`open_id`,`is_lock`,`remark`,`honor`,`exp`,`fans_num`,`follow_num`,`oid`,`health`,`ban_time`,`update_time`,`create_time`,`is_verify`,`status`) VALUES (1, '123', '管理员', '888888', '1', 99, 'mr.djun@qq.com', '0', '123', NULL, NULL, '18683827876', '0', '', NULL, NULL, '0', NULL, 400, 0, 0, 0, '3', 100, NULL, NULL, 1567842558, '1', '1'),(2, 'test', NULL, NULL, NULL, NULL, '123456@qq.com', '0', 'test', NULL, NULL, '15120016876', '0', '', NULL, NULL, '0', NULL, 400, 0, 0, 0, '3', 100, NULL, NULL, 1567842558, '0', '1');
UNLOCK TABLES;
COMMIT;
