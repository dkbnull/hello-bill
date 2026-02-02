/*
 * Copyright (c) 2017-2026 null. All rights reserved.
 */

CREATE TABLE `income_info`
(
    `id`           bigint(20) unsigned NOT NULL COMMENT '收入信息ID',
    `username`     varchar(20)    NOT NULL COMMENT '用户名',
    `income_date`  date           NOT NULL COMMENT '收入日期',
    `top_class`    varchar(8)     NOT NULL COMMENT '顶级分类',
    `second_class` varchar(8)     NOT NULL COMMENT '二级分类',
    `detail`       varchar(100)   NOT NULL COMMENT '详情',
    `amount`       decimal(10, 2) NOT NULL COMMENT '收入金额',
    `remark`       varchar(100)            DEFAULT NULL COMMENT '备注',
    `gmt_create`   timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    KEY            `idx_income_info_id_username` (`id`,`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='收入信息表';