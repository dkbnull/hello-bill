CREATE TABLE IF NOT EXISTS `import_bill_detail_convert`
(
    `id`            BIGINT(20) NOT NULL,
    `username`      VARCHAR(20)  NOT NULL,
    `detail`        VARCHAR(100) NOT NULL,
    `detailConvert` VARCHAR(100) NOT NULL,
    `createTime`    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updateTime`    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);
