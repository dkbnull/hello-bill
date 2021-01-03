package cn.wbnull.hellobill.db.entity;

import cn.wbnull.hellobill.common.constant.UtilConstants;
import cn.wbnull.hellobill.common.model.expend.AddRequestModel;
import cn.wbnull.hellobill.common.util.DateUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
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
public class ExpendInfo extends Model<ExpendInfo> {

    private static final long serialVersionUID = 1L;

    @TableId
    private String uuid;

    private String username;

    @JsonFormat(pattern = UtilConstants.DATE_FORMAT)
    @TableField("expendTime")
    private LocalDateTime expendTime;

    @TableField("topClass")
    private String topClass;

    @TableField("secondClass")
    private String secondClass;

    private String detail;

    private String amount;

    private String remark;

    public static ExpendInfo build(AddRequestModel request, String topClass) {
        ExpendInfo expendInfo = new ExpendInfo();
        expendInfo.uuid = UUID.randomUUID().toString();
        expendInfo.username = request.getUsername();
        expendInfo.expendTime = DateUtils.localDateTimeParse(request.getExpendTime());
        expendInfo.topClass = topClass;
        expendInfo.secondClass = request.getSecondClass();
        expendInfo.detail = request.getDetail();
        expendInfo.amount = request.getAmount();
        expendInfo.remark = request.getRemark();

        return expendInfo;
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

    public LocalDateTime getExpendTime() {
        return expendTime;
    }

    public void setExpendTime(LocalDateTime expendTime) {
        this.expendTime = expendTime;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
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
