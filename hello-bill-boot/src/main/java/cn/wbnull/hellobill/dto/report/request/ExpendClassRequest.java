package cn.wbnull.hellobill.dto.report.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 支出分类报表查询接口请求参数
 *
 * @author null
 * @date 2024-02-11
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
@Data
public class ExpendClassRequest {

    @NotEmpty(message = "reportDate 不能为空")
    private String reportDate;

    private String topClass;
}
