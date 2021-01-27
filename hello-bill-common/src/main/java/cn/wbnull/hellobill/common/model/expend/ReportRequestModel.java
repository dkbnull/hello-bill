package cn.wbnull.hellobill.common.model.expend;

import cn.wbnull.hellobill.common.model.RequestModel;
import com.alibaba.fastjson.JSON;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 支出、收入信息报表接口请求参数
 *
 * @author dukunbiao(null)  2021-01-26
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class ReportRequestModel extends RequestModel {

    @NotEmpty(message = "reportDate 不能为空")
    private String reportDate;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
