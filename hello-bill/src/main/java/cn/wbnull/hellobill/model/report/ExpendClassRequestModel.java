package cn.wbnull.hellobill.model.report;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 支出分类报表查询接口请求参数
 *
 * @author dukunbiao(null)  2024-02-11
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class ExpendClassRequestModel {

    @NotEmpty(message = "reportDate 不能为空")
    private String reportDate;

    private String topClass;
}
