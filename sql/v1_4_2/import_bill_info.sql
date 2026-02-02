/*
 * Copyright (c) 2017-2026 null. All rights reserved.
 */

CREATE TABLE `import_bill_info`
(
    `id`             bigint(20) unsigned NOT NULL COMMENT '导入信息ID',
    `username`       varchar(20)    NOT NULL COMMENT '用户名',
    `bill_type`      tinyint(1) unsigned DEFAULT NULL COMMENT '账单类型 0-支出; 1-收入',
    `bill_time`      datetime       NOT NULL COMMENT '账单时间',
    `top_class`      varchar(8)              DEFAULT NULL COMMENT '顶级分类',
    `second_class`   varchar(8)              DEFAULT NULL COMMENT '二级分类',
    `detail`         varchar(100)   NOT NULL COMMENT '详情',
    `detail_convert` varchar(100)   NOT NULL COMMENT '转换后详情',
    `amount`         decimal(10, 2) NOT NULL COMMENT '账单金额',
    `pay_mode`       varchar(50)             DEFAULT NULL COMMENT '支付方式',
    `remark`         varchar(100)            DEFAULT NULL COMMENT '备注',
    `gmt_create`     timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`   timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='账单导入信息表';