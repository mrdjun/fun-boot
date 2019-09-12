/*
	Database: fun
*/
SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `fun`.`sys_login_log`;
DROP TABLE IF EXISTS `fun`.`sys_oper_log`;
DROP TABLE IF EXISTS `fun`.`ums_user`;
CREATE TABLE `sys_login_log` (
  `info_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(25) DEFAULT NULL COMMENT '登录账号',
  `login_location` varchar(100) DEFAULT NULL COMMENT '登录地点',
  `ipaddr` varchar(50) DEFAULT NULL COMMENT 'ip地址',
  `os` varchar(50) DEFAULT NULL COMMENT '操作系统',
  `browser` varchar(50) DEFAULT NULL COMMENT '浏览器',
  `msg` varchar(255) DEFAULT NULL,
  `login_time` bigint(15) DEFAULT NULL COMMENT '访问时间',
  `status` char(1) DEFAULT '0' COMMENT '0-成功1-失败',
  PRIMARY KEY (`info_id`)
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4;
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
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `login_ip` varchar(30) CHARACTER SET utf8 DEFAULT '' COMMENT '最后登陆IP',
  `login_date` varchar(13) DEFAULT NULL COMMENT '最后登陆时间',
  `open_id` varchar(30) DEFAULT NULL COMMENT '开放id',
  `is_lock` char(1) DEFAULT '0' COMMENT '0-可修改u号1-不可修改',
  `remark` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '备注',
  `honor` int(3) DEFAULT '400' COMMENT '信誉度',
  `exp` int(10) DEFAULT '0' COMMENT '经验',
  `fans_num` int(11) DEFAULT '0' COMMENT '粉丝量',
  `follow_num` int(11) DEFAULT '0' COMMENT '关注量',
  `oid` char(1) DEFAULT '4' COMMENT '个人信息公开程度',
  `health` int(2) DEFAULT '100' COMMENT '健康度',
  `ban_time` bigint(13) DEFAULT NULL COMMENT '禁用账号到期时间',
  `update_time` bigint(13) DEFAULT NULL,
  `create_time` bigint(13) DEFAULT NULL,
  `is_verify` char(1) DEFAULT '0' COMMENT '1-已认证，0-未认证',
  `status` char(1) DEFAULT '1' COMMENT '0-禁用1-正常',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
BEGIN;
LOCK TABLES `fun`.`sys_login_log` WRITE;
DELETE FROM `fun`.`sys_login_log`;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `fun`.`sys_oper_log` WRITE;
DELETE FROM `fun`.`sys_oper_log`;
INSERT INTO `fun`.`sys_oper_log` (`oper_id`,`oper_name`,`oper_ip`,`oper_location`,`error_msg`,`login_name`,`time`,`method`,`oper_param`,`create_time`,`status`) VALUES (14, '获取用户列表', '127.0.0.1', '内网IP', NULL, 'admin', 165, 'com.fun.project.system.controller.UserController.getUserList()', '{}', 1568129451558, '1');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `fun`.`ums_user` WRITE;
DELETE FROM `fun`.`ums_user`;
INSERT INTO `fun`.`ums_user` (`user_id`,`login_name`,`username`,`u_account`,`user_type`,`user_level`,`email`,`sex`,`password`,`salt`,`avatar`,`telephone`,`del_flag`,`login_ip`,`login_date`,`open_id`,`is_lock`,`remark`,`honor`,`exp`,`fans_num`,`follow_num`,`oid`,`health`,`ban_time`,`update_time`,`create_time`,`is_verify`,`status`) VALUES (1, 'admin', '管理员', '888888', '1', 99, 'mr.djun@qq.com', '0', 'admin', NULL, NULL, '18683827876', '0', '', NULL, NULL, '0', NULL, 400, 0, 0, 0, '3', 100, NULL, NULL, 1567842558, '1', '1'),(2, 'test', NULL, NULL, NULL, NULL, '123456@qq.com', '0', 'test', NULL, NULL, '15120016876', '0', '', NULL, NULL, '0', NULL, 400, 0, 0, 0, '3', 100, NULL, NULL, 1567842558, '0', '1');
UNLOCK TABLES;
COMMIT;
