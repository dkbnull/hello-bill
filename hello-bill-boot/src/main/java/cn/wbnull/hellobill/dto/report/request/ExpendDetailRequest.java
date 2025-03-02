package cn.wbnull.hellobill.dto.report.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 支出详情报表查询接口请求参数
 *
 * @author null  2024-02-12
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class ExpendDetailRequest {

    @NotEmpty(message = "reportDate 不能为空")
    private String reportDate;

    private String topClass;
    private String secondClass;
}
