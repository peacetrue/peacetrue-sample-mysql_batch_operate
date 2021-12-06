create table `user`(
    `id`   bigint unsigned primary key auto_increment comment '主键',
    `name` varchar(255) not null comment '用户名'
) comment '用户名' engine = InnoDB default charset = utf8mb4;

