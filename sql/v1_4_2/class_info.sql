/*
 * Copyright (c) 2017-2026 null. All rights reserved.
 */

CREATE TABLE `class_info`
(
    `uuid`         varchar(36) NOT NULL COMMENT '主键ID',
    `top_class`    varchar(8)  NOT NULL COMMENT '一级分类',
    `second_class` varchar(8)  NOT NULL COMMENT '二级分类',
    `type`         tinyint(1) unsigned NOT NULL COMMENT '分类类型 0-支出; 1-收入',
    `serial_no`    int(11) DEFAULT NULL COMMENT '序号',
    `status`       varchar(1)  NOT NULL COMMENT '状态 0-不启用; 1-启用',
    `gmt_create`   timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='分类信息表';