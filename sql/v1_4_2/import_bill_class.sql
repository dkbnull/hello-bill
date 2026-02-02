/*
 * Copyright (c) 2017-2026 null. All rights reserved.
 */

CREATE TABLE `import_bill_class`
(
    `id`           bigint(20) unsigned NOT NULL COMMENT '主键ID',
    `detail`       varchar(100) NOT NULL COMMENT '详情',
    `top_class`    varchar(8)   NOT NULL COMMENT '顶级分类',
    `second_class` varchar(8)   NOT NULL COMMENT '二级分类',
    `gmt_create`   timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='账单导入分类表';