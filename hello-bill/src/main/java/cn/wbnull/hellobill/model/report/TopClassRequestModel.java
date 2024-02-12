package cn.wbnull.hellobill.model.report;

import cn.wbnull.hellobill.common.model.RequestModel;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * 分类报表查询接口请求参数
 *
 * @author dukunbiao(null)  2024-02-11
 * https://github.com/dkbnull/HelloBill
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TopClassRequestModel extends RequestModel {

    @NotEmpty(message = "type 不能为空")
    private String type;

    @NotEmpty(message = "reportDate 不能为空")
    private String reportDate;

    private String topClass;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
