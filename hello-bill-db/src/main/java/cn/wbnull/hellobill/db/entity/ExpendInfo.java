package cn.wbnull.hellobill.db.entity;

import cn.wbnull.hellobill.common.constant.UtilConstants;
import cn.wbnull.hellobill.common.model.expend.AddRequestModel;
import cn.wbnull.hellobill.common.model.expend.UpdateRequestModel;
import cn.wbnull.hellobill.common.util.DateUtils;
import cn.wbnull.hellobill.common.util.SnowflakeUtils;
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
 * @author dukunbiao(null)
 * @since 2024-11-22
 */
@Getter
@Setter
@TableName("expend_info")
public class ExpendInfo {

    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @TableField("username")
    private String username;

    @JSONField(format = UtilConstants.DATE_FORMAT)
    @JsonFormat(pattern = UtilConstants.DATE_FORMAT)
    @TableField("expendTime")
    private LocalDateTime expendTime;

    @TableField("topClass")
    private String topClass;

    @TableField("secondClass")
    private String secondClass;

    @TableField("detail")
    private String detail;

    @TableField("amount")
    private BigDecimal amount;

    @TableField("remark")
    private String remark;

    @TableField("createTime")
    private LocalDateTime createTime;

    @TableField("updateTime")
    private LocalDateTime updateTime;

    public static ExpendInfo build(String username, AddRequestModel request, String topClass) {
        ExpendInfo expendInfo = new ExpendInfo();

        expendInfo.expendTime = DateUtils.localDateTimeParse(request.getExpendTime());

        assert expendInfo.expendTime != null;
        long epochMilli = DateUtils.toEpochMilli(expendInfo.expendTime);
        expendInfo.id = SnowflakeUtils.getInstance().nextId(epochMilli);
        expendInfo.username = username;
        expendInfo.topClass = topClass;
        expendInfo.secondClass = request.getSecondClass();
        expendInfo.detail = request.getDetail();
        expendInfo.amount = new BigDecimal(request.getAmount());
        expendInfo.remark = request.getRemark();

        return expendInfo;
    }

    public void update(UpdateRequestModel request, String topClass) {
        this.expendTime = DateUtils.localDateTimeParse(request.getExpendTime());
        this.topClass = topClass;
        this.secondClass = request.getSecondClass();
        this.detail = request.getDetail();
        this.amount = new BigDecimal(request.getAmount());
        this.remark = request.getRemark();
    }
}
