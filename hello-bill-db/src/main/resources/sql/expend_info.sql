CREATE TABLE `expend_info`
(
    `id`          bigint(20) NOT NULL,
    `username`    varchar(20)    NOT NULL,
    `expendTime`  datetime       NOT NULL,
    `topClass`    varchar(8)     NOT NULL,
    `secondClass` varchar(8)     NOT NULL,
    `detail`      varchar(100)   NOT NULL,
    `amount`      decimal(10, 2) NOT NULL,
    `remark`      varchar(100)            DEFAULT NULL,
    `createTime`  timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updateTime`  timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;