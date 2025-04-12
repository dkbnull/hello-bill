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
 *
 * </p>
 *
 * @author null
 * @since 2025-04-12
 */
@Getter
@Setter
@TableName("balance_sheet")
public class BalanceSheet {

    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @TableField("username")
    private String username;

    @TableField("balance_date")
    private LocalDate balanceDate;

    @TableField("income_amount")
    private BigDecimal incomeAmount;

    @TableField("expend_amount")
    private BigDecimal expendAmount;

    @TableField("balance_amount")
    private BigDecimal balanceAmount;

    @TableField("remark")
    private String remark;

    @TableField("gmt_create")
    private LocalDateTime gmtCreate;

    @TableField("gmt_modified")
    private LocalDateTime gmtModified;
}
