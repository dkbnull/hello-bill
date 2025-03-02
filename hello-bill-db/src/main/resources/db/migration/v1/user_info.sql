CREATE TABLE `user_info`
(
    `id`           int(10) unsigned zerofill NOT NULL AUTO_INCREMENT,
    `username`     varchar(20)  NOT NULL,
    `salt`         varchar(32)  NOT NULL,
    `password`     varchar(100) NOT NULL,
    `mobile`       varchar(16)           DEFAULT NULL,
    `gmt_create`   timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `gmt_modified` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);