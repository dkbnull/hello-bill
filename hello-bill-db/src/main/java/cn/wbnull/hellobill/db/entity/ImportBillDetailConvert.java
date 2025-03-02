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
@TableName("import_bill_detail_convert")
public class ImportBillDetailConvert {

    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @TableField("username")
    private String username;

    @TableField("detail")
    private String detail;

    @TableField("detail_convert")
    private String detailConvert;

    @TableField("gmt_create")
    private LocalDateTime gmtCreate;

    @TableField("gmt_modified")
    private LocalDateTime gmtModified;
}
