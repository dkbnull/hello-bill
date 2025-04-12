CREATE TABLE `income_info` (
    `id` BIGINT(20) UNSIGNED NOT NULL COMMENT '收入信息ID',
    `username` VARCHAR(20) NOT NULL COMMENT '用户名',
    `income_date` DATE NOT NULL COMMENT '收入日期',
    `top_class` VARCHAR(8) NOT NULL COMMENT '顶级分类',
    `second_class` VARCHAR(8) NOT NULL COMMENT '二级分类',
    `detail` VARCHAR(100) NOT NULL COMMENT '详情',
    `amount` DECIMAL(10 , 2 ) NOT NULL COMMENT '收入金额',
    `remark` VARCHAR(100) DEFAULT NULL COMMENT '备注',
    `gmt_create` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
)  COMMENT='收入信息表';

ALTER TABLE `income_info`
ADD INDEX `idx_income_info_id_username` (`id` ASC, `username` ASC) VISIBLE;
