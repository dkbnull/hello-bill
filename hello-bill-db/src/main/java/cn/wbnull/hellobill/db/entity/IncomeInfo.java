package cn.wbnull.hellobill.db.entity;

import cn.wbnull.hellobill.common.model.income.AddRequestModel;
import cn.wbnull.hellobill.common.model.income.UpdateRequestModel;
import cn.wbnull.hellobill.common.util.DateUtils;
import cn.wbnull.hellobill.common.util.SnowflakeUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 *
 * </p>
 *
 * @author dukunbiao(null)
 * @since 2021-01-01
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class IncomeInfo extends Model<IncomeInfo> {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    private String username;

    @TableField("incomeDate")
    private LocalDate incomeDate;

    @TableField("topClass")
    private String topClass;

    @TableField("secondClass")
    private String secondClass;

    private String detail;

    private String amount;

    private String remark;

    public static IncomeInfo build(String username, AddRequestModel request, String topClass) {
        IncomeInfo incomeInfo = new IncomeInfo();
        incomeInfo.id = SnowflakeUtils.getInstance().nextId();
        incomeInfo.username = username;
        incomeInfo.incomeDate = DateUtils.localDateParse(request.getIncomeDate());
        incomeInfo.topClass = topClass;
        incomeInfo.secondClass = request.getSecondClass();
        incomeInfo.detail = request.getDetail();
        incomeInfo.amount = request.getAmount();
        incomeInfo.remark = request.getRemark();

        return incomeInfo;
    }

    public void update(UpdateRequestModel request, String topClass) {
        this.incomeDate = DateUtils.localDateParse(request.getIncomeDate());
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
