package cn.wbnull.hellobill.model.report;

import cn.wbnull.hellobill.common.model.RequestModel;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * 支出详情报表查询接口请求参数
 *
 * @author null  2024-02-12
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class ExpendDetailRequestModel {

    @NotEmpty(message = "reportDate 不能为空")
    private String reportDate;

    private String topClass;

    private String secondClass;
}
