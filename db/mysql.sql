-- 用户表
DROP TABLE IF EXISTS `yisi_user`;
CREATE TABLE `yisi_user`(
	`id` bigint not null primary key comment 'id',
	`user` varchar(255) not null comment '用户名',
	`password` varchar(255) not null comment '密码',
	`display_name` varchar(20) DEFAULT NULL COMMENT '显示的用户名',
	`update_date` datetime default null comment '更新时间',
	`updater`	bigint default null comment '更新者',
	`last_login_time` datetime DEFAULT NULL comment '上次登录时间',
	`status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '正常1，禁止登录0, 已删除-1',
	`create_date` datetime DEFAULT null comment '创建时间'
)ENGINE=innodb DEFAULT CHARSET=utf8 COMMENT='用户表';

INSERT INTO `yisi_user` values(1,'admin','admin','管理员',null,null,null,1,'2019-9-30 08:35:18');


-- 角色表
DROP TABLE IF EXISTS `yisi_role`;
CREATE TABLE `yisi_role`(
	`id` bigint not null primary key comment 'id',
	`name` varchar(50) not null comment '角色标识 这个是唯一的',
	`description` varchar(255) default null comment '描述信息'
) ENGINE=innodb DEFAULT CHARSET=utf8 COMMENT='角色表';

INSERT INTO `yisi_role` values(1,'admin','管理员用户');
INSERT INTO `yisi_role` values(2,'normal','普通用户');

-- 用户角色中间表
DROP TABLE IF EXISTS `yisi_user_role`;
CREATE TABLE `yisi_user_role`(
	`id` bigint not null primary key auto_increment comment 'id',
	`user_id` bigint not null comment '用户表ID',
	`role_id` bigint not null default '2' comment '角色表ID'
)ENGINE=innodb DEFAULT CHARSET=utf8 COMMENT='用户角色中间表';

INSERT INTO `yisi_user_role` VALUES(1,1,1);


-- 权限表
DROP TABLE IF EXISTS `yisi_permission`;
CREATE TABLE `yisi_permission`(
	`id` bigint primary key comment 'id',
	`name` varchar(255) not null comment '权限名',
	`description` varchar(255) default null comment '描述信息'
)ENGINE=innodb DEFAULT CHARSET=utf8 COMMENT='权限表';
-- 权限比较简单 就区分一下 是不是管理员即可
INSERT INTO `yisi_permission` values(1,'admin:*','拥有所有权限');
INSERT INTO `yisi_permission` values(2,'user:*','普通用户权限');

-- 角色权限中间表
DROP TABLE IF EXISTS `yisi_role_permission`;
CREATE TABLE `yisi_role_permission`(
	`id` bigint not null primary key auto_increment comment 'id',
	`permission_id` bigint not null comment '权限表ID',
	`role_id` bigint not null default '2' comment '角色表ID'
)ENGINE=innodb DEFAULT CHARSET=utf8 COMMENT='角色权限中间表';

INSERT INTO `yisi_role_permission` VALUES(1,1,1);
INSERT INTO `yisi_role_permission` VALUES(2,2,1);

 -- 文章表 评论表
DROP TABLE IF EXISTS `yisi_context`;
CREATE TABLE `yisi_context`(
	`id` bigint not null primary key comment 'id',
	`pid` bigint comment '-1文章表 如不为-1 则为对应ID评论',
	`title` varchar(50) comment '标题',
	`context` text not null comment '内容',
	`update_date` datetime default null comment '更新时间',
	`updater`	bigint default null comment '更新者',
	`create_date` datetime DEFAULT null comment '创建时间',
	`creater` bigint comment '创建者'
)ENGINE=innodb DEFAULT CHARSET=utf8 COMMENT='文章评论表';