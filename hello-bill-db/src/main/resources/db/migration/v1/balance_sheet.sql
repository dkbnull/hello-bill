CREATE TABLE `balance_sheet` (
    `id` BIGINT(20) UNSIGNED NOT NULL COMMENT '资产负债信息ID',
    `username` VARCHAR(20) NOT NULL COMMENT '用户名',
    `balance_date` DATE NOT NULL COMMENT '资产负债日期',
    `income_amount` DECIMAL(10 , 2 ) NOT NULL COMMENT '本期收入金额',
    `expend_amount` DECIMAL(10 , 2 ) NOT NULL COMMENT '本期支出金额',
    `balance_amount` DECIMAL(10 , 2 ) NOT NULL COMMENT '账户余额',
    `remark` VARCHAR(100) DEFAULT NULL COMMENT '备注',
    `gmt_create` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
)  COMMENT='资产负债信息表';