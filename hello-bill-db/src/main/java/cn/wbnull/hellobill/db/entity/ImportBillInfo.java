/*
 * Copyright (c) 2017-2025 null. All rights reserved.
 */

package cn.wbnull.hellobill.db.entity;

import cn.wbnull.hellobill.common.core.constant.UtilConstants;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
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
@TableName("import_bill_info")
public class ImportBillInfo {

    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @TableField("username")
    private String username;

    @TableField("bill_type")
    private Byte billType;

    @JSONField(format = UtilConstants.DATE_FORMAT)
    @JsonFormat(pattern = UtilConstants.DATE_FORMAT)
    @TableField("bill_time")
    private LocalDateTime billTime;

    @TableField("top_class")
    private String topClass;

    @TableField("second_class")
    private String secondClass;

    @TableField("detail")
    private String detail;

    @TableField("detail_convert")
    private String detailConvert;

    @TableField("amount")
    private BigDecimal amount;

    @TableField("pay_mode")
    private String payMode;

    @TableField("remark")
    private String remark;

    @TableField("gmt_create")
    private LocalDateTime gmtCreate;

    @TableField("gmt_modified")
    private LocalDateTime gmtModified;
}
