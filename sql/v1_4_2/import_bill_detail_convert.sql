/*
 * Copyright (c) 2017-2026 null. All rights reserved.
 */

CREATE TABLE `import_bill_detail_convert`
(
    `id`             bigint(20) unsigned NOT NULL COMMENT '主键ID',
    `username`       varchar(20)  NOT NULL COMMENT '用户名',
    `detail`         varchar(100) NOT NULL COMMENT '详情',
    `detail_convert` varchar(100) NOT NULL COMMENT '转换后详情',
    `gmt_create`     timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`   timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='账单导入详情转换表';