CREATE TABLE IF NOT EXISTS `import_bill_class`
(
    `id`          bigint(20) NOT NULL,
    `detail`      varchar(100) NOT NULL,
    `topClass`    varchar(8)   NOT NULL,
    `secondClass` varchar(8)   NOT NULL,
    `createTime`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updateTime`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- 初始化数据 --
INSERT INTO import_bill_class(SELECT id,
                                     detail,
                                     topClass,
                                     secondClass,
                                     createTime,
                                     updateTime
                              FROM expend_info ei
                                       JOIN
                                   (SELECT MAX(id) AS maxId
                                    FROM expend_info
                                    GROUP BY detail) AS dei ON ei.id = dei.maxId);