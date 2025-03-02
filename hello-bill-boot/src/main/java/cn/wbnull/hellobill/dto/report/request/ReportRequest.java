package cn.wbnull.hellobill.dto.report.request;

import lombok.Data;

/**
 * 支出、收入信息报表接口请求参数
 *
 * @author null
 * @date 2021-01-26
 * @link <a href="https://github.com/dkbnull/HelloBill">GitHub</a>
 */
@Data
public class ReportRequest {

    private String reportDate;
    private String reportClass;
}
