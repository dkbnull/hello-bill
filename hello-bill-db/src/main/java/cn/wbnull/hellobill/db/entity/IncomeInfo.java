package cn.wbnull.hellobill.db.entity;

import cn.wbnull.hellobill.common.model.income.AddRequestModel;
import cn.wbnull.hellobill.common.model.income.UpdateRequestModel;
import cn.wbnull.hellobill.common.util.DateUtils;
import cn.wbnull.hellobill.common.util.SnowflakeUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
@TableName("income_info")
public class IncomeInfo {

    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @TableField("username")
    private String username;

    @TableField("incomeDate")
    private LocalDate incomeDate;

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

    public static IncomeInfo build(String username, AddRequestModel request, String topClass) {
        IncomeInfo incomeInfo = new IncomeInfo();

        incomeInfo.incomeDate = DateUtils.localDateParse(request.getIncomeDate());
        LocalTime time = LocalTime.of(0, 0, 0);
        assert incomeInfo.incomeDate != null;
        LocalDateTime dateTime = incomeInfo.incomeDate.atTime(time);
        long epochMilli = DateUtils.toEpochMilli(dateTime);

        incomeInfo.id = SnowflakeUtils.getInstance().nextId(epochMilli);
        incomeInfo.username = username;
        incomeInfo.topClass = topClass;
        incomeInfo.secondClass = request.getSecondClass();
        incomeInfo.detail = request.getDetail();
        incomeInfo.amount = new BigDecimal(request.getAmount());
        incomeInfo.remark = request.getRemark();

        return incomeInfo;
    }

    public void update(UpdateRequestModel request, String topClass) {
        this.incomeDate = DateUtils.localDateParse(request.getIncomeDate());
        this.topClass = topClass;
        this.secondClass = request.getSecondClass();
        this.detail = request.getDetail();
        this.amount = new BigDecimal(request.getAmount());
        this.remark = request.getRemark();
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
