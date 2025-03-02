CREATE TABLE `expend_info`
(
    `id`           bigint(20) unsigned NOT NULL,
    `username`     varchar(20)    NOT NULL,
    `expend_time`  datetime       NOT NULL,
    `top_class`    varchar(8)     NOT NULL,
    `second_class` varchar(8)     NOT NULL,
    `detail`       varchar(100)   NOT NULL,
    `amount`       decimal(10, 2) NOT NULL,
    `remark`       varchar(100)            DEFAULT NULL,
    `gmt_create`   timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `gmt_modified` timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);