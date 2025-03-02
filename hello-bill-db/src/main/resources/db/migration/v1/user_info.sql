CREATE TABLE IF NOT EXISTS `user_info`
(
    `id`         int(10) unsigned zerofill NOT NULL AUTO_INCREMENT,
    `username`   varchar(20)  NOT NULL,
    `salt`       varchar(32)  NOT NULL,
    `password`   varchar(100) NOT NULL,
    `mobile`     varchar(16)           DEFAULT NULL,
    `createTime` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updateTime` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000000004 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;