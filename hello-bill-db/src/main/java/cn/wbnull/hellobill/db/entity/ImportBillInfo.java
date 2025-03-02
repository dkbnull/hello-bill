package cn.wbnull.hellobill.db.entity;

import cn.wbnull.hellobill.common.constant.UtilConstants;
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
 * @since 2025-01-25
 */
@Getter
@Setter
@TableName("import_bill_info")
public class ImportBillInfo {

    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @TableField("username")
    private String username;

    /**
     * 0 - 支出
     * 1 - 收入
     * 2 - 未知
     */
    @TableField("billType")
    private Byte billType;

    @JSONField(format = UtilConstants.DATE_FORMAT)
    @JsonFormat(pattern = UtilConstants.DATE_FORMAT)
    @TableField("billTime")
    private LocalDateTime billTime;

    @TableField("topClass")
    private String topClass;

    @TableField("secondClass")
    private String secondClass;

    @TableField("detail")
    private String detail;

    @TableField("detailConvert")
    private String detailConvert;

    @TableField("amount")
    private BigDecimal amount;

    @TableField("payMode")
    private String payMode;

    @TableField("remark")
    private String remark;

    @TableField("createTime")
    private LocalDateTime createTime;

    @TableField("updateTime")
    private LocalDateTime updateTime;
}
