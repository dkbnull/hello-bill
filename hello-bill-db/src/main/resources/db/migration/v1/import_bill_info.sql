CREATE TABLE `import_bill_info` (
    `id` BIGINT(20) UNSIGNED NOT NULL COMMENT '导入信息ID',
    `username` VARCHAR(20) NOT NULL COMMENT '用户名',
    `bill_type` TINYINT(1) UNSIGNED DEFAULT NULL COMMENT '账单类型 0-支出; 1-收入',
    `bill_time` DATETIME NOT NULL COMMENT '账单时间',
    `top_class` VARCHAR(8) DEFAULT NULL COMMENT '顶级分类',
    `second_class` VARCHAR(8) DEFAULT NULL COMMENT '二级分类',
    `detail` VARCHAR(100) NOT NULL COMMENT '详情',
    `detail_convert` VARCHAR(100) NOT NULL COMMENT '转换后详情',
    `amount` DECIMAL(10 , 2 ) NOT NULL COMMENT '账单金额',
    `pay_mode` VARCHAR(50) DEFAULT NULL COMMENT '支付方式',
    `remark` VARCHAR(100) DEFAULT NULL COMMENT '备注',
    `gmt_create` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
)  COMMENT='账单导入信息表';