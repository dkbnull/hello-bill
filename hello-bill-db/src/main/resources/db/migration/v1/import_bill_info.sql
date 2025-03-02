CREATE TABLE IF NOT EXISTS `import_bill_info`
(
    `id`            bigint(20) NOT NULL,
    `username`      varchar(20)    NOT NULL,
    `billType`      tinyint(1) DEFAULT NULL,
    `billTime`      datetime       NOT NULL,
    `topClass`      varchar(8)              DEFAULT NULL,
    `secondClass`   varchar(8)              DEFAULT NULL,
    `detail`        varchar(100)   NOT NULL,
    `detailConvert` varchar(100)   NOT NULL,
    `amount`        decimal(10, 2) NOT NULL,
    `payMode`       varchar(50)             DEFAULT NULL,
    `remark`        varchar(100)            DEFAULT NULL,
    `createTime`    timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updateTime`    timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;