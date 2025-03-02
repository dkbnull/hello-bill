package cn.wbnull.hellobill.dto.report.request;

import lombok.Data;

/**
 * 支出、收入信息报表接口请求参数
 *
 * @author null  2021-01-26
 * https://github.com/dkbnull/HelloBill
 */
@Data
public class ReportRequest {

    private String reportDate;
    private String reportClass;
}
