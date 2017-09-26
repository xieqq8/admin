-- phpMyAdmin SQL Dump
-- version 4.1.12
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: 2017-09-25 09:19:36
-- 服务器版本： 5.6.16
-- PHP Version: 5.5.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `admin_manage`
--

-- --------------------------------------------------------

--
-- 表的结构 `menu`
--

CREATE TABLE IF NOT EXISTS `menu` (
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

--
-- 转存表中的数据 `menu`
--

INSERT INTO `menu` (`id`, `label`, `path`, `order`, `level`, `url`, `type`, `style`, `disabled`) VALUES
('blog', '博客管理', '0', 20, 1, '/blogsite/blog', 0, NULL, 0),
('blog_articles', '博文管理', '0,blog', 21, 2, '/blogsite/blog', 0, NULL, 0),
('blog_book', '博文书', '0,blog', 24, 2, '/blogsite/book', 0, NULL, 0),
('blog_botconfig', '/blogsite/siteconfig', '0,blog', 25, 2, '/blogsite/siteconfig', 0, NULL, 0),
('blog_catalog', '博客分类', '0,blog', 22, 2, '/blogsite/catalog', 0, NULL, 0),
('blog_nav', '博客导航侧边的', '0,blog', 23, 2, '/blogsite/nav', 0, NULL, 0),
('menu', '菜单管理', '0,system', 11, 1, '/menu', 0, NULL, 0),
('monitor', '系统监控', '0,system', 15, 2, '/admin', 0, NULL, 0),
('resource', '资源管理', '0,system', 14, 4, '/resource', 0, NULL, 0),
('role', '角色管理', '0,system', 13, 3, '/role', 0, NULL, 0),
('syslog', '系统日志', '0,system', 16, 5, '/syslog', 0, NULL, 0),
('system', '系统管理', '0', 10, 1, '/', 0, NULL, 0),
('user', '用户管理', '0,system', 12, 2, '/user', 0, NULL, 0);

-- --------------------------------------------------------

--
-- 表的结构 `resource`
--

CREATE TABLE IF NOT EXISTS `resource` (
  `id` varchar(50) NOT NULL COMMENT '编码',
  `title` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '标题',
  `disabled` smallint(6) NOT NULL DEFAULT '0',
  `url` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '地址',
  `description` varchar(80) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='权限表';

--
-- 转存表中的数据 `resource`
--

INSERT INTO `resource` (`id`, `title`, `disabled`, `url`, `description`) VALUES
('0ca38fc9-32b8-4fbf-acb1-56a0c0d4941a', '菜单管理', 0, '/menu/**', '菜单管理所有权限'),
('c0fd3fa0-0298-4942-8f58-1b6ecf299b7a', '用户管理', 0, '/user/**', '用户管理员'),
('52c9fe93-4de8-4cf8-9612-e94e5c8e6993', '角色管理权限', 0, '/role/**', '角色管理权限描述'),
('7889acde-5c70-43d0-9e6a-7ebf0c9e8459', '根目录', 0, '/', '根目录，大家都可以访问的'),
('c512952f-b523-4f44-accb-70b6920a0fbb', '系统监控', 0, '/admin/**', '系统监控权限'),
('42a8a5c3-531b-4ffe-84db-a9ddf72e45d4', '系统日志', 0, '/syslog/**', '系统日志描述'),
('c4fd6346-aa05-4740-9178-43ae616d9538', '资源', 0, '/resource/**', '资源管理'),
('2e81d538-b302-4c60-bc96-92b18b1ead34', '网站博客', 0, '/blogsite/blog/**', '博客管理'),
('2d4c6201-c46b-4de6-a3eb-17c0a55ac6a7', '网站书', 0, '/blogsite/book/**', '书'),
('dbb8ddbe-237f-4879-8f37-c331b8621a41', '网站目录', 0, '/blogsite/catalog/**', '网站目录'),
('febb413a-427f-43d1-9496-0f68d0fb5227', '网站导航', 0, '/blogsite/nav/**', '网站导航');

-- --------------------------------------------------------

--
-- 表的结构 `role`
--

CREATE TABLE IF NOT EXISTS `role` (
  `id` varchar(50) NOT NULL,
  `name` varchar(30) NOT NULL COMMENT '角色',
  `disabled` smallint(6) NOT NULL DEFAULT '0',
  `description` varchar(60) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `rolename` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

--
-- 转存表中的数据 `role`
--

INSERT INTO `role` (`id`, `name`, `disabled`, `description`) VALUES
('1', 'radmin', 0, '管理员'),
('10642827-c2d5-460c-be1e-cd76e26a6cee', 'ruser_admin', 0, '用户管理员，只可以加用户的'),
('f1d07c8f-57e9-4e00-a03f-348a96cd54e2', 'rblog_admin', 0, '博客管理员');

-- --------------------------------------------------------

--
-- 表的结构 `role_menu`
--

CREATE TABLE IF NOT EXISTS `role_menu` (
  `role_id` varchar(50) DEFAULT NULL,
  `menu_id` varchar(50) DEFAULT NULL,
  KEY `role_id_rm` (`role_id`),
  KEY `menu_code_rm` (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `role_menu`
--

INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES
('f1d07c8f-57e9-4e00-a03f-348a96cd54e2', 'blog'),
('f1d07c8f-57e9-4e00-a03f-348a96cd54e2', 'blog_articles'),
('f1d07c8f-57e9-4e00-a03f-348a96cd54e2', 'blog_book'),
('f1d07c8f-57e9-4e00-a03f-348a96cd54e2', 'blog_botconfig'),
('f1d07c8f-57e9-4e00-a03f-348a96cd54e2', 'blog_catalog'),
('f1d07c8f-57e9-4e00-a03f-348a96cd54e2', 'blog_nav'),
('f1d07c8f-57e9-4e00-a03f-348a96cd54e2', 'menu'),
('f1d07c8f-57e9-4e00-a03f-348a96cd54e2', 'monitor'),
('f1d07c8f-57e9-4e00-a03f-348a96cd54e2', 'resource'),
('f1d07c8f-57e9-4e00-a03f-348a96cd54e2', 'role'),
('f1d07c8f-57e9-4e00-a03f-348a96cd54e2', 'syslog'),
('f1d07c8f-57e9-4e00-a03f-348a96cd54e2', 'system'),
('f1d07c8f-57e9-4e00-a03f-348a96cd54e2', 'user'),
('1', 'blog'),
('1', 'blog_articles'),
('1', 'blog_book'),
('1', 'blog_botconfig'),
('1', 'blog_catalog'),
('1', 'blog_nav'),
('1', 'menu'),
('1', 'monitor'),
('1', 'resource'),
('1', 'role'),
('1', 'syslog'),
('1', 'system'),
('1', 'user'),
('10642827-c2d5-460c-be1e-cd76e26a6cee', 'menu'),
('10642827-c2d5-460c-be1e-cd76e26a6cee', 'system'),
('10642827-c2d5-460c-be1e-cd76e26a6cee', 'user');

-- --------------------------------------------------------

--
-- 表的结构 `role_resource`
--

CREATE TABLE IF NOT EXISTS `role_resource` (
  `role_id` varchar(50) DEFAULT NULL,
  `resource_id` varchar(50) DEFAULT NULL,
  KEY `roleid_rr` (`role_id`),
  KEY `resource_rr` (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `role_resource`
--

INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES
('f1d07c8f-57e9-4e00-a03f-348a96cd54e2', '0ca38fc9-32b8-4fbf-acb1-56a0c0d4941a'),
('f1d07c8f-57e9-4e00-a03f-348a96cd54e2', 'c0fd3fa0-0298-4942-8f58-1b6ecf299b7a'),
('f1d07c8f-57e9-4e00-a03f-348a96cd54e2', '52c9fe93-4de8-4cf8-9612-e94e5c8e6993'),
('f1d07c8f-57e9-4e00-a03f-348a96cd54e2', '7889acde-5c70-43d0-9e6a-7ebf0c9e8459'),
('f1d07c8f-57e9-4e00-a03f-348a96cd54e2', 'c512952f-b523-4f44-accb-70b6920a0fbb'),
('f1d07c8f-57e9-4e00-a03f-348a96cd54e2', '42a8a5c3-531b-4ffe-84db-a9ddf72e45d4'),
('f1d07c8f-57e9-4e00-a03f-348a96cd54e2', 'c4fd6346-aa05-4740-9178-43ae616d9538'),
('f1d07c8f-57e9-4e00-a03f-348a96cd54e2', '2e81d538-b302-4c60-bc96-92b18b1ead34'),
('f1d07c8f-57e9-4e00-a03f-348a96cd54e2', '2d4c6201-c46b-4de6-a3eb-17c0a55ac6a7'),
('f1d07c8f-57e9-4e00-a03f-348a96cd54e2', 'dbb8ddbe-237f-4879-8f37-c331b8621a41'),
('f1d07c8f-57e9-4e00-a03f-348a96cd54e2', 'febb413a-427f-43d1-9496-0f68d0fb5227'),
('1', '0ca38fc9-32b8-4fbf-acb1-56a0c0d4941a'),
('1', 'c0fd3fa0-0298-4942-8f58-1b6ecf299b7a'),
('1', '52c9fe93-4de8-4cf8-9612-e94e5c8e6993'),
('1', '7889acde-5c70-43d0-9e6a-7ebf0c9e8459'),
('1', 'c512952f-b523-4f44-accb-70b6920a0fbb'),
('1', '42a8a5c3-531b-4ffe-84db-a9ddf72e45d4'),
('1', 'c4fd6346-aa05-4740-9178-43ae616d9538'),
('1', '2e81d538-b302-4c60-bc96-92b18b1ead34'),
('1', '2d4c6201-c46b-4de6-a3eb-17c0a55ac6a7'),
('1', 'dbb8ddbe-237f-4879-8f37-c331b8621a41'),
('1', 'febb413a-427f-43d1-9496-0f68d0fb5227'),
('10642827-c2d5-460c-be1e-cd76e26a6cee', '0ca38fc9-32b8-4fbf-acb1-56a0c0d4941a'),
('10642827-c2d5-460c-be1e-cd76e26a6cee', 'c0fd3fa0-0298-4942-8f58-1b6ecf299b7a'),
('10642827-c2d5-460c-be1e-cd76e26a6cee', '52c9fe93-4de8-4cf8-9612-e94e5c8e6993'),
('10642827-c2d5-460c-be1e-cd76e26a6cee', '7889acde-5c70-43d0-9e6a-7ebf0c9e8459'),
('10642827-c2d5-460c-be1e-cd76e26a6cee', 'c512952f-b523-4f44-accb-70b6920a0fbb'),
('10642827-c2d5-460c-be1e-cd76e26a6cee', '42a8a5c3-531b-4ffe-84db-a9ddf72e45d4'),
('10642827-c2d5-460c-be1e-cd76e26a6cee', 'c4fd6346-aa05-4740-9178-43ae616d9538'),
('10642827-c2d5-460c-be1e-cd76e26a6cee', '2e81d538-b302-4c60-bc96-92b18b1ead34'),
('10642827-c2d5-460c-be1e-cd76e26a6cee', '2d4c6201-c46b-4de6-a3eb-17c0a55ac6a7'),
('10642827-c2d5-460c-be1e-cd76e26a6cee', 'dbb8ddbe-237f-4879-8f37-c331b8621a41'),
('10642827-c2d5-460c-be1e-cd76e26a6cee', 'febb413a-427f-43d1-9496-0f68d0fb5227');

-- --------------------------------------------------------

--
-- 表的结构 `syslog`
--

CREATE TABLE IF NOT EXISTS `syslog` (
  `uid` varchar(50) DEFAULT NULL COMMENT '用户ID',
  `content` varchar(600) NOT NULL DEFAULT '' COMMENT '日志内容',
  `operation` varchar(250) DEFAULT NULL COMMENT '用户操作',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `user` varchar(50) DEFAULT NULL COMMENT '用户'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='操作日志表';

--
-- 转存表中的数据 `syslog`
--

INSERT INTO `syslog` (`uid`, `content`, `operation`, `createTime`, `user`) VALUES
('878a22a2-84cc-437c-ad33-51c71cb901bb', 'content', 'add', '2017-07-02 00:00:00', 'blogadmin');

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

CREATE TABLE IF NOT EXISTS `user` (
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

--
-- 转存表中的数据 `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `email`, `salt`, `disabled`, `createTime`, `lastTime`) VALUES
('1', 'root', '5442b02dabc5ed9401be4dfe1ca8adb9', 'fafafggag', 'r', 0, '2016-09-27 19:53:20', '2016-09-27 19:53:22'),
('51d4966e-715a-4291-b9f0-72d63b640d4f', 'admin', '04d766c7eb2c18dd3496010eecd9856d', '', '"J.JP^g7bc', 0, '2017-07-28 10:48:56', NULL),
('ac923287-bb2b-4701-9f3c-5b9a82c4a60f', 'user', 'b8aea264fbfa49730198ffdb8b411dc8', '', '.o?jaL)z~~', 0, '2017-08-10 10:11:04', NULL),
('f7cb16bc-7942-4176-9015-89b756d54b8e', 'blogadmin', '3e59a4046df81f3c270fa056774a039c', 'blogadmin@admin.com', '0Pt%Yz}7L}', 0, '2017-08-10 10:04:35', NULL);

-- --------------------------------------------------------

--
-- 表的结构 `user_role`
--

CREATE TABLE IF NOT EXISTS `user_role` (
  `uid` varchar(50) NOT NULL,
  `role_id` varchar(50) NOT NULL,
  KEY `uid` (`uid`),
  KEY `role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `user_role`
--

INSERT INTO `user_role` (`uid`, `role_id`) VALUES
('1', '1'),
('7c77d2fb-b5f2-4bdc-9018-ab4090d05868', '10642827-c2d5-460c-be1e-cd76e26a6cee'),
('7c77d2fb-b5f2-4bdc-9018-ab4090d05868', 'f1d07c8f-57e9-4e00-a03f-348a96cd54e2'),
('a5242300-fe34-468e-8ade-3c2f130852d8', '10642827-c2d5-460c-be1e-cd76e26a6cee'),
('878a22a2-84cc-437c-ad33-51c71cb901bb', 'f1d07c8f-57e9-4e00-a03f-348a96cd54e2'),
('f7cb16bc-7942-4176-9015-89b756d54b8e', 'f1d07c8f-57e9-4e00-a03f-348a96cd54e2'),
('ac923287-bb2b-4701-9f3c-5b9a82c4a60f', '10642827-c2d5-460c-be1e-cd76e26a6cee'),
('51d4966e-715a-4291-b9f0-72d63b640d4f', '1');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
