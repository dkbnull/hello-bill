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
 * 账单导入信息表
 * </p>
 *
 * @author null
 * @since 2025-03-02
 */
@Getter
@Setter
@TableName("import_bill_info")
public class ImportBillInfo {

    /**
     * 导入信息ID
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 账单类型 0-支出; 1-收入
     */
    @TableField("bill_type")
    private Byte billType;

    /**
     * 账单时间
     */
    @JSONField(format = UtilConstants.DATE_FORMAT)
    @JsonFormat(pattern = UtilConstants.DATE_FORMAT)
    @TableField("bill_time")
    private LocalDateTime billTime;

    /**
     * 顶级分类
     */
    @TableField("top_class")
    private String topClass;

    /**
     * 二级分类
     */
    @TableField("second_class")
    private String secondClass;

    /**
     * 详情
     */
    @TableField("detail")
    private String detail;

    /**
     * 转换后详情
     */
    @TableField("detail_convert")
    private String detailConvert;

    /**
     * 账单金额
     */
    @TableField("amount")
    private BigDecimal amount;

    /**
     * 支付方式
     */
    @TableField("pay_mode")
    private String payMode;

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
