package cn.wbnull.hellobill.db.entity;

import cn.wbnull.hellobill.common.model.income.AddRequestModel;
import cn.wbnull.hellobill.common.util.DateUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * <p>
 *
 * </p>
 *
 * @author dukunbiao(null)
 * @since 2021-01-01
 */
public class IncomeInfo extends Model<IncomeInfo> {

    private static final long serialVersionUID = 1L;

    @TableId
    private String uuid;

    private String username;

    @TableField("incomeDate")
    private LocalDate incomeDate;

    @TableField("topClass")
    private String topClass;

    @TableField("secondClass")
    private String secondClass;

    private String detail;

    private BigDecimal amount;

    private String remark;

    public static IncomeInfo build(AddRequestModel request, String topClass) {
        IncomeInfo incomeInfo = new IncomeInfo();
        incomeInfo.uuid = UUID.randomUUID().toString();
        incomeInfo.username = request.getUsername();
        incomeInfo.incomeDate = DateUtils.localDateParse(request.getIncomeDate());
        incomeInfo.topClass = topClass;
        incomeInfo.secondClass = request.getSecondClass();
        incomeInfo.detail = request.getDetail();
        incomeInfo.amount = new BigDecimal(request.getAmount());
        incomeInfo.remark = request.getRemark();

        return incomeInfo;
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

    public LocalDate getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(LocalDate incomeDate) {
        this.incomeDate = incomeDate;
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
