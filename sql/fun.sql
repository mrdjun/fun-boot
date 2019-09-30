/*
MySQL Backup
Database: fun_base
Backup Time: 2019-09-15 22:49:14
*/

SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `fun_base`.`sys_login_log`;
DROP TABLE IF EXISTS `fun_base`.`sys_menu`;
DROP TABLE IF EXISTS `fun_base`.`sys_oper_log`;
DROP TABLE IF EXISTS `fun_base`.`sys_role`;
DROP TABLE IF EXISTS `fun_base`.`sys_role_menu`;
DROP TABLE IF EXISTS `fun_base`.`sys_user`;
DROP TABLE IF EXISTS `fun_base`.`sys_user_role`;
DROP TABLE IF EXISTS `fun_base`.`ums_user`;
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
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4;
CREATE TABLE `sys_menu` (
  `menu_id` int(11) NOT NULL,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
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
LOCK TABLES `fun_base`.`sys_login_log` WRITE;
DELETE FROM `fun_base`.`sys_login_log`;
INSERT INTO `fun_base`.`sys_login_log` (`info_id`,`login_name`,`login_location`,`ipaddr`,`os`,`browser`,`msg`,`create_time`,`status`) VALUES (4, 'admin', '内网IP', '127.0.0.1', 'Unknown', 'Unknown', '登录成功', 1568359184694, '0'),(5, 'admin', '内网IP', '127.0.0.1', 'Unknown', 'Unknown', '退出登录', 1568359193453, '0'),(33, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1568538521726, '0'),(34, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1568538662409, '0'),(35, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1568539069378, '0'),(36, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1568539252356, '0'),(37, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1568539252497, '0'),(38, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1568555391422, '0'),(39, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1568555428697, '0'),(40, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1568555429287, '0'),(41, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1568556030623, '0'),(42, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1568556031564, '0'),(43, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1568556036287, '0'),(44, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1568556038304, '0'),(45, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1568556365043, '0'),(46, 'admin', '内网IP', '127.0.0.1', 'Windows 10', 'Chrome', '登录成功', 1568556365043, '0'),(47, 'admin', '内网IP', '127.0.0.1', 'Unknown', 'Unknown', '用户不存在/密码错误', 1568557253455, '1'),(48, 'admin', '内网IP', '127.0.0.1', 'Unknown', 'Unknown', '登录成功', 1568557320840, '0');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `fun_base`.`sys_menu` WRITE;
DELETE FROM `fun_base`.`sys_menu`;
INSERT INTO `fun_base`.`sys_menu` (`menu_id`,`menu_name`,`parent_id`,`order_num`,`url`,`target`,`menu_type`,`visible`,`perms`,`icon`,`create_by`,`create_time`,`update_by`,`update_time`,`remark`) VALUES (1, '系统管理', 0, 1, '#', '', 'M', '0', 'system:user:view', '#', 'admin', NULL, '', NULL, NULL),(2, '系统监控', 0, 2, '#', '', 'M', '0', 'system:user:view', '#', 'admin', NULL, '', NULL, NULL);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `fun_base`.`sys_oper_log` WRITE;
DELETE FROM `fun_base`.`sys_oper_log`;
INSERT INTO `fun_base`.`sys_oper_log` (`oper_id`,`oper_name`,`oper_ip`,`oper_location`,`error_msg`,`login_name`,`time`,`method`,`oper_param`,`create_time`,`status`) VALUES (14, '获取用户列表', '127.0.0.1', '内网IP', NULL, 'admin', 165, 'com.fun.project.system.controller.UserController.getUserList()', '{}', 1568129451558, '1'),(15, '执行 Redis keysSize 命令', '127.0.0.1', '内网IP', NULL, 'admin', 19, 'com.fun.project.admin.monitor.controller.RedisController.getKeysSize()', '{}', 1568557413000, '1');
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
INSERT INTO `fun_base`.`sys_user` (`user_id`,`login_name`,`username`,`email`,`telephone`,`sex`,`avatar`,`password`,`salt`,`status`,`del_flag`,`login_ip`,`login_date`,`create_by`,`create_time`,`update_by`,`update_time`,`remark`) VALUES (1, 'admin', '超级管理员', 'mr.djun@qq.com', '18683827876', '0', '/profile/avatar/2019/09/06/11864680aedaf7e8dd08d52cdc816849.png', 'b35b89afc0e805106fdc6aeb0bc0f00f', '888888', '1', '1', '127.0.0.1', 1568557320851, 'system', NULL, '', NULL, NULL);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `fun_base`.`sys_user_role` WRITE;
DELETE FROM `fun_base`.`sys_user_role`;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `fun_base`.`ums_user` WRITE;
DELETE FROM `fun_base`.`ums_user`;
INSERT INTO `fun_base`.`ums_user` (`user_id`,`login_name`,`username`,`u_account`,`user_type`,`user_level`,`email`,`sex`,`password`,`salt`,`avatar`,`telephone`,`del_flag`,`login_ip`,`login_date`,`open_id`,`is_lock`,`remark`,`honor`,`exp`,`fans_num`,`follow_num`,`oid`,`health`,`ban_time`,`update_time`,`create_time`,`is_verify`,`status`) VALUES (1, '123', '管理员', '888888', '1', 99, 'mr.djun@qq.com', '0', '123', NULL, NULL, '18683827876', '0', '', NULL, NULL, '0', NULL, 400, 0, 0, 0, '3', 100, NULL, NULL, 1567842558, '1', '1'),(2, 'test', NULL, NULL, NULL, NULL, '123456@qq.com', '0', 'test', NULL, NULL, '15120016876', '0', '', NULL, NULL, '0', NULL, 400, 0, 0, 0, '3', 100, NULL, NULL, 1567842558, '0', '1');
UNLOCK TABLES;
COMMIT;
