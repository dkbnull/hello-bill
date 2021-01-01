package cn.wbnull.hellobill.db.entity;

import cn.wbnull.hellobill.common.constant.UtilConstants;
import cn.wbnull.hellobill.common.model.bill.AddRequestModel;
import cn.wbnull.hellobill.common.util.DateUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>
 *
 * </p>
 *
 * @author dukunbiao(null)
 * @since 2020-12-31
 */
public class BillInfo extends Model<BillInfo> {

    private static final long serialVersionUID = 1L;

    private String uuid;

    private String username;

    @JsonFormat(pattern = UtilConstants.DATE_FORMAT)
    @TableField("billTime")
    private LocalDateTime billTime;

    @TableField("topClass")
    private String topClass;

    @TableField("secondClass")
    private String secondClass;

    private String detail;

    private BigDecimal amount;

    private String remark;

    public static BillInfo build(AddRequestModel request, String topClass) {
        BillInfo billInfo = new BillInfo();
        billInfo.uuid = UUID.randomUUID().toString();
        billInfo.username = request.getUsername();
        billInfo.billTime = DateUtils.localDateTimeParse(request.getBillTime());
        billInfo.topClass = topClass;
        billInfo.secondClass = request.getSecondClass();
        billInfo.detail = request.getDetail();
        billInfo.amount = new BigDecimal(request.getAmount());
        billInfo.remark = request.getRemark();

        return billInfo;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getBillTime() {
        return billTime;
    }

    public void setBillTime(LocalDateTime billTime) {
        this.billTime = billTime;
    }

    public String getTopClass() {
        return topClass;
    }

    public void setTopClass(String topClass) {
        this.topClass = topClass;
    }

    public String getSecondClass() {
        return secondClass;
    }

    public void setSecondClass(String secondClass) {
        this.secondClass = secondClass;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    protected Serializable pkVal() {
        return this.uuid;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
