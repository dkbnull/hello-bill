CREATE TABLE `import_bill_info`
(
    `id`             bigint(20) unsigned NOT NULL,
    `username`       varchar(20)    NOT NULL,
    `bill_type`      tinyint(1) unsigned DEFAULT NULL,
    `bill_time`      datetime       NOT NULL,
    `top_class`      varchar(8)              DEFAULT NULL,
    `second_class`   varchar(8)              DEFAULT NULL,
    `detail`         varchar(100)   NOT NULL,
    `detail_convert` varchar(100)   NOT NULL,
    `amount`         decimal(10, 2) NOT NULL,
    `pay_mode`       varchar(50)             DEFAULT NULL,
    `remark`         varchar(100)            DEFAULT NULL,
    `gmt_create`     timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `gmt_modified`   timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);