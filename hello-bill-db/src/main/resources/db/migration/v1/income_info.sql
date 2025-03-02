CREATE TABLE `income_info`
(
    `id`           bigint(20) unsigned NOT NULL,
    `username`     varchar(20)    NOT NULL,
    `income_date`  date           NOT NULL,
    `top_class`    varchar(8)     NOT NULL,
    `second_class` varchar(8)     NOT NULL,
    `detail`       varchar(100)   NOT NULL,
    `amount`       decimal(10, 2) NOT NULL,
    `remark`       varchar(100)            DEFAULT NULL,
    `gmt_create`   timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `gmt_modified` timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

ALTER TABLE `income_info`
ADD INDEX `idx_income_info_id_username` (`id` ASC, `username` ASC) VISIBLE;
;
