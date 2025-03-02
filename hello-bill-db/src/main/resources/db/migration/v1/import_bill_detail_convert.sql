CREATE TABLE `import_bill_detail_convert`
(
    `id`             bigint(20) unsigned NOT NULL,
    `username`       varchar(20)  NOT NULL,
    `detail`         varchar(100) NOT NULL,
    `detail_convert` varchar(100) NOT NULL,
    `gmt_create`     timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `gmt_modified`   timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);