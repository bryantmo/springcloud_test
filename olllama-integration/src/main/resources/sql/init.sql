-- mp3文件路径存储
create table music_path(
    id int primary key auto_increment,
    `type` tinyint(2) not null default 0 comment '类型，0-默认，1-宝宝，2-儿童，3-女孩，4-男孩，5-女士，6-男士',
    `age` int not null default 0 comment '年龄',
    `path` varchar(100) not null default '' comment '本地路径',
    `origin_text` text  comment '音频原始文本',
    `voice` longtext comment 'base64编码后的音频数据',
    key idx_on_type_age_name(`type`,`age`,`path`(20))
)  engine = innodb DEFAULT CHARSET=utf8mb4 comment '音频表';
