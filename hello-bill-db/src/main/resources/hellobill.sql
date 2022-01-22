CREATE TABLE `class_info`
(
    `uuid`        varchar(36) NOT NULL,
    `topClass`    varchar(8)  NOT NULL,
    `secondClass` varchar(8)  NOT NULL,
    `type`        varchar(1)  NOT NULL,
    PRIMARY KEY (`uuid`),
    UNIQUE KEY `secondClass_UNIQUE` (`secondClass`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `class_info`
VALUES ('00000000-0000-0000-0000-000000000000', '日常支出', '衣', '0'),
       ('00000000-0000-0000-0000-000000000001', '日常支出', '食', '0'),
       ('00000000-0000-0000-0000-000000000002', '日常支出', '住', '0'),
       ('00000000-0000-0000-0000-000000000003', '日常支出', '行', '0'),
       ('00000000-0000-0000-0000-000000000004', '日常支出', '用', '0'),
       ('00000000-0000-0000-0000-000000000005', '日常支出', '玩', '0'),
       ('00000000-0000-0000-0000-000000000006', '日常支出', '学', '0'),
       ('00000000-0000-0000-0001-000000000000', '固定支出', '房贷', '0'),
       ('00000000-0000-0000-0001-000000000001', '固定支出', '车贷', '0'),
       ('00000000-0000-0000-0001-000000000002', '固定支出', '医保', '0'),
       ('00000000-0000-0000-0001-000000000003', '固定支出', '社保', '0'),
       ('00000000-0000-0000-0001-000000000004', '固定支出', '公积金', '0'),
       ('00000000-0000-0001-0000-000000000000', '收入', '工资', '1'),
       ('00000000-0000-0001-0000-000000000001', '收入', '私活', '1');

CREATE TABLE `expend_info`
(
    `uuid`        varchar(36)    NOT NULL,
    `username`    varchar(20)    NOT NULL,
    `expendTime`  datetime       NOT NULL,
    `topClass`    varchar(8)     NOT NULL,
    `secondClass` varchar(8)     NOT NULL,
    `detail`      varchar(100)   NOT NULL,
    `amount`      decimal(10, 2) NOT NULL,
    `remark`      varchar(100) DEFAULT NULL,
    PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `income_info`
(
    `uuid`        varchar(36)    NOT NULL,
    `username`    varchar(20)    NOT NULL,
    `incomeDate`  date           NOT NULL,
    `topClass`    varchar(8)     NOT NULL,
    `secondClass` varchar(8)     NOT NULL,
    `detail`      varchar(100)   NOT NULL,
    `amount`      decimal(10, 2) NOT NULL,
    `remark`      varchar(100) DEFAULT NULL,
    PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_info`
(
    `username` varchar(20) NOT NULL,
    `password` varchar(45) NOT NULL,
    PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `user_info`
VALUES ('admin', 'E10ADC3949BA59ABBE56E057F20F883E');

/********************************************** 2022-01-05 **********************************************/

ALTER TABLE `class_info`
    ADD COLUMN `serialNo` INT NULL AFTER `type`,ADD COLUMN `status` VARCHAR(1) NULL AFTER `serialNo`;
