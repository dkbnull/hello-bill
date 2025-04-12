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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 资产负债信息表
 * </p>
 *
 * @author null
 * @since 2025-04-12
 */
@Getter
@Setter
@TableName("balance_sheet")
public class BalanceSheet {

    /**
     * 资产负债信息ID
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 资产负债日期
     */
    @TableField("balance_date")
    private LocalDate balanceDate;

    /**
     * 本期收入金额
     */
    @TableField("income_amount")
    private BigDecimal incomeAmount;

    /**
     * 本期支出金额
     */
    @TableField("expend_amount")
    private BigDecimal expendAmount;

    /**
     * 账户余额
     */
    @TableField("balance_amount")
    private BigDecimal balanceAmount;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 创建时间
     */
    @TableField("gmt_create")
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField("gmt_modified")
    private LocalDateTime gmtModified;
}
