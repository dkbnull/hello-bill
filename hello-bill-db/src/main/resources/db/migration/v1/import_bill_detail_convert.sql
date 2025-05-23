CREATE TABLE `import_bill_detail_convert` (
    `id` BIGINT(20) UNSIGNED NOT NULL COMMENT '主键ID',
    `username` VARCHAR(20) NOT NULL COMMENT '用户名',
    `detail` VARCHAR(100) NOT NULL COMMENT '详情',
    `detail_convert` VARCHAR(100) NOT NULL COMMENT '转换后详情',
    `gmt_create` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
)  COMMENT='账单导入详情转换表';