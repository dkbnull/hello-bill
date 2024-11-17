package cn.wbnull.hellobill.db.entity;

import cn.wbnull.hellobill.common.constant.UtilConstants;
import cn.wbnull.hellobill.common.model.expend.AddRequestModel;
import cn.wbnull.hellobill.common.model.expend.UpdateRequestModel;
import cn.wbnull.hellobill.common.util.DateUtils;
import cn.wbnull.hellobill.common.util.SnowflakeUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author dukunbiao(null)
 * @since 2020-12-31
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ExpendInfo extends Model<ExpendInfo> {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    private String username;

    @JSONField(format = UtilConstants.DATE_FORMAT)
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

    public static ExpendInfo build(String username, AddRequestModel request, String topClass) {
        ExpendInfo expendInfo = new ExpendInfo();
        expendInfo.id = SnowflakeUtils.getInstance().nextId();
        expendInfo.username = username;
        expendInfo.expendTime = DateUtils.localDateTimeParse(request.getExpendTime());
        expendInfo.topClass = topClass;
        expendInfo.secondClass = request.getSecondClass();
        expendInfo.detail = request.getDetail();
        expendInfo.amount = request.getAmount();
        expendInfo.remark = request.getRemark();

        return expendInfo;
    }

    public void update(UpdateRequestModel request, String topClass) {
        this.expendTime = DateUtils.localDateTimeParse(request.getExpendTime());
        this.topClass = topClass;
        this.secondClass = request.getSecondClass();
        this.detail = request.getDetail();
        this.amount = request.getAmount();
        this.remark = request.getRemark();
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
