CREATE TABLE `import_bill_class` (
    `id` BIGINT(20) UNSIGNED NOT NULL COMMENT '主键ID',
    `detail` VARCHAR(100) NOT NULL COMMENT '详情',
    `top_class` VARCHAR(8) NOT NULL COMMENT '顶级分类',
    `second_class` VARCHAR(8) NOT NULL COMMENT '二级分类',
    `gmt_create` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
)  COMMENT='账单导入分类表';

-- 初始化数据 --
INSERT INTO import_bill_class(SELECT id,
                                     detail,
                                     top_class,
                                     second_class,
                                     create_time,
                                     update_time
                              FROM expend_info ei
                                       JOIN
                                   (SELECT MAX(id) AS max_id
                                    FROM expend_info
                                    GROUP BY detail) AS dei ON ei.id = dei.max_id);