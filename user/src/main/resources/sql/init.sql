CREATE TABLE `user` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name` varchar(255) NOT NULL DEFAULT '' COMMENT '账号',
    `age` int(2) NOT NULL DEFAULT 0 COMMENT '年龄',
    `email` varchar(255) NOT NULL DEFAULT '' COMMENT '邮箱',
    `tenant_id` bigint(20) NOT NULL DEFAULT 1 COMMENT '租户id，1表示默认租户',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

create table country (
	`id` bigint(20) not null primary key comment '主键ID',
	`name` varchar(200) default '' not null comment '名字',
	`position` varchar(200) default '' not null comment 'geohash'
) engine = innodb default charset = utf8mb4 collate = utf8mb4_unicode_ci comment = '国家表';