CREATE TABLE `import_bill_class`
(
    `id`           bigint(20) unsigned NOT NULL,
    `detail`       varchar(100) NOT NULL,
    `top_class`    varchar(8)   NOT NULL,
    `second_class` varchar(8)   NOT NULL,
    `gmt_create`   timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `gmt_modified` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);


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