/*
 * Copyright (c) 2017-2026 null. All rights reserved.
 */

CREATE TABLE `user_info`
(
    `id`           int(10) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username`     varchar(20)  NOT NULL COMMENT '用户名',
    `salt`         varchar(32)  NOT NULL COMMENT '盐值',
    `password`     varchar(100) NOT NULL COMMENT '密码',
    `mobile`       varchar(16)           DEFAULT NULL COMMENT '手机',
    `gmt_create`   timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    KEY            `idx_user_info_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=1000000004 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户信息表';