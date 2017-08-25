/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : admin_manage

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2016-11-11 15:09:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `menu`
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` varchar(50) NOT NULL,
  `label` varchar(20) NOT NULL,
  `path` varchar(200) DEFAULT '0',
  `order` smallint(6) DEFAULT '1',
  `level` smallint(6) DEFAULT '1' COMMENT '层级，方便根据层级查询',
  `url` varchar(200) DEFAULT NULL,
  `type` smallint(6) DEFAULT '1' COMMENT '扩展不同菜单时用',
  `style` varchar(50) DEFAULT NULL COMMENT 'ui 样式',
  `disabled` smallint(6) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('fa', 'fa', '0', '1', '1', 'fafa', '1', null, '1');
INSERT INTO `menu` VALUES ('menu', '菜单管理', '0,system', '2', '1', '/menu', '1', null, '0');
INSERT INTO `menu` VALUES ('monitor', '系统监控', '0,system', '5', '2', '/admin', '2', null, '0');
INSERT INTO `menu` VALUES ('resource', '资源管理', '0,system', '2', '4', '/resource', '1', null, '0');
INSERT INTO `menu` VALUES ('role', '角色管理', '0,system', '2', '3', '/role', '1', null, '0');
INSERT INTO `menu` VALUES ('syslog', '系统日志', '0,system', '6', '5', '/syslog', '1', null, '0');
INSERT INTO `menu` VALUES ('system', '系统管理', '0', '1', '1', '', '1', null, '0');
INSERT INTO `menu` VALUES ('user', '用户管理', '0,system', '2', '2', '/user', '1', null, '0');

-- ----------------------------
-- Table structure for `resource`
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `id` varchar(50) NOT NULL COMMENT '编码',
  `title` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '标题',
  `disabled` smallint(6) NOT NULL DEFAULT '0',
  `url` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '地址',
  `description` varchar(80) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Records of resource
-- ----------------------------
INSERT INTO `resource` VALUES ('admin', 'fa', '0', '/user/*', 'fagaga');
INSERT INTO `resource` VALUES ('a6675696-1322-4005-a4e5-5a824f3a6129', 'gaga', '0', 'gaga', 'gaga');
INSERT INTO `resource` VALUES ('c0fd3fa0-0298-4942-8f58-1b6ecf299b7a', 'faf', '0', 'fac', 'fa3');
INSERT INTO `resource` VALUES ('423cd1d5-6eb6-45d8-96c2-6f856fa13039', 'ga', '0', 'gagaga', 'gaga');

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` varchar(50) NOT NULL,
  `name` varchar(30) NOT NULL COMMENT '角色',
  `disabled` smallint(6) NOT NULL DEFAULT '0',
  `description` varchar(60) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `rolename` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'admin', '0', 'fa');
INSERT INTO `role` VALUES ('f1d07c8f-57e9-4e00-a03f-348a96cd54e2', 'fa', '0', 'fagagagaga');

-- ----------------------------
-- Table structure for `role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `role_id` varchar(50) DEFAULT NULL,
  `menu_id` varchar(50) DEFAULT NULL,
  KEY `role_id_rm` (`role_id`),
  KEY `menu_code_rm` (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES ('1', 'menu');
INSERT INTO `role_menu` VALUES ('1', 'resource');
INSERT INTO `role_menu` VALUES ('1', 'role');
INSERT INTO `role_menu` VALUES ('1', 'syslog');
INSERT INTO `role_menu` VALUES ('1', 'system');
INSERT INTO `role_menu` VALUES ('1', 'user');
INSERT INTO `role_menu` VALUES ('f1d07c8f-57e9-4e00-a03f-348a96cd54e2', 'menu');

-- ----------------------------
-- Table structure for `role_resource`
-- ----------------------------
DROP TABLE IF EXISTS `role_resource`;
CREATE TABLE `role_resource` (
  `role_id` varchar(50) DEFAULT NULL,
  `resource_id` varchar(50) DEFAULT NULL,
  KEY `roleid_rr` (`role_id`),
  KEY `resource_rr` (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_resource
-- ----------------------------
INSERT INTO `role_resource` VALUES ('1', 'c0fd3fa0-0298-4942-8f58-1b6ecf299b7a');
INSERT INTO `role_resource` VALUES ('f1d07c8f-57e9-4e00-a03f-348a96cd54e2', 'a6675696-1322-4005-a4e5-5a824f3a6129');

-- ----------------------------
-- Table structure for `syslog`
-- ----------------------------
DROP TABLE IF EXISTS `syslog`;
CREATE TABLE `syslog` (
  `uid` varchar(50) DEFAULT NULL COMMENT '用户ID',
  `content` varchar(600) NOT NULL DEFAULT '' COMMENT '日志内容',
  `operation` varchar(250) DEFAULT NULL COMMENT '用户操作',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `user` varchar(50) DEFAULT NULL COMMENT '用户'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='操作日志表';

-- ----------------------------
-- Records of syslog
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(50) NOT NULL COMMENT '主键ID',
  `username` varchar(20) NOT NULL COMMENT '登录名称',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `email` varchar(60) DEFAULT NULL COMMENT '邮箱',
  `salt` varchar(50) DEFAULT '0' COMMENT '密码的盐',
  `disabled` smallint(6) NOT NULL DEFAULT '1' COMMENT '0、禁用 1、正常',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastTime` datetime DEFAULT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `loginname` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'root', '5442b02dabc5ed9401be4dfe1ca8adb9', 'fafafggag', 'r', '0', '2016-09-27 19:53:20', '2016-09-27 19:53:22');
INSERT INTO `user` VALUES ('8891e12f-81a7-43cd-8ab8-4accdf141f96', 'rootga', '2e9ca4d0b1a586fd80bc2ba782ac36bc', 'gagagag', '0', '0', '2016-10-31 21:16:47', null);
INSERT INTO `user` VALUES ('aaf62456-d96c-4aae-bff0-90330a7d7a02', 'rootffa', '52d76781a799f857f35e3bb50c94c21e', 'gag', '0', '0', '2016-11-04 18:12:34', null);

-- ----------------------------
-- Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `uid` varchar(50) NOT NULL,
  `role_id` varchar(50) NOT NULL,
  KEY `uid` (`uid`),
  KEY `role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1');
INSERT INTO `user_role` VALUES ('8891e12f-81a7-43cd-8ab8-4accdf141f96', 'f1d07c8f-57e9-4e00-a03f-348a96cd54e2');
