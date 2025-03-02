/*
 * Copyright (c) 2017-2025 null. All rights reserved.
 */

package cn.wbnull.hellobill.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author null
 * @since 2025-03-02
 */
@Getter
@Setter
@TableName("import_bill_class")
public class ImportBillClass {

    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @TableField("detail")
    private String detail;

    @TableField("top_class")
    private String topClass;

    @TableField("second_class")
    private String secondClass;

    @TableField("gmt_create")
    private LocalDateTime gmtCreate;

    @TableField("gmt_modified")
    private LocalDateTime gmtModified;
}
