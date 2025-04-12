CREATE TABLE `expend_info` (
    `id` BIGINT(20) UNSIGNED NOT NULL COMMENT '支出信息ID',
    `username` VARCHAR(20) NOT NULL COMMENT '用户名',
    `expend_time` DATETIME NOT NULL COMMENT '支出时间',
    `top_class` VARCHAR(8) NOT NULL COMMENT '顶级分类',
    `second_class` VARCHAR(8) NOT NULL COMMENT '二级分类',
    `detail` VARCHAR(100) NOT NULL COMMENT '详情',
    `amount` DECIMAL(10 , 2 ) NOT NULL COMMENT '金额',
    `remark` VARCHAR(100) DEFAULT NULL COMMENT '备注',
    `gmt_create` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
)  COMMENT='支出信息表';

ALTER TABLE `expend_info`
ADD INDEX `idx_expend_info_id_username` (`id` ASC, `username` ASC) VISIBLE;
