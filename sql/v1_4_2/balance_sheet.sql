/*
 * Copyright (c) 2017-2026 null. All rights reserved.
 */

CREATE TABLE `balance_sheet`
(
    `id`             bigint(20) unsigned NOT NULL COMMENT '资产负债信息ID',
    `username`       varchar(20)    NOT NULL COMMENT '用户名',
    `balance_date`   date           NOT NULL COMMENT '资产负债日期',
    `income_amount`  decimal(10, 2) NOT NULL COMMENT '本期收入金额',
    `expend_amount`  decimal(10, 2) NOT NULL COMMENT '本期支出金额',
    `balance_amount` decimal(10, 2) NOT NULL COMMENT '账户余额',
    `remark`         varchar(100)            DEFAULT NULL COMMENT '备注',
    `gmt_create`     timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`   timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='资产负债信息表';